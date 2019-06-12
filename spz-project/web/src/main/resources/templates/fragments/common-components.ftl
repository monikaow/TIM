<#assign spring=JspTaglibs["http://www.springframework.org/tags"]>

<script type="text/x-template" id="vc-section-header">
    <v-card class="elevation-2 mt-2 mb-3" :max-width="maxWidth">
        <v-card-text class="px-4">
            <section>
                <h2 class="title font-weight-regular ellipsis primary--text pb-1 mb-3" v-text="title"></h2>
                <slot></slot>
            </section>
        </v-card-text>
    </v-card>
</script>
<script>
    Vue.component('vc-section-header', {
        template: '#vc-section-header',
        props: ['title', 'max-width']
    });
</script>
<script type="text/x-template" id="vc-section-headline">
    <div>
        <h2 class="body-2 ellipsis secondary--text mb-1" v-text="title"></h2>
        <v-card class="elevation-2 mt-2 mb-3">
            <v-card-text class="px-4">
                <section>
                    <slot></slot>
                </section>
            </v-card-text>
        </v-card>
    </div>
</script>
<script>
    Vue.component('vc-section-headline', {
        template: '#vc-section-headline',
        props: ['title']
    });
</script>
<script type="text/x-template" id="vc-empty-section-headline">
    <div>
        <h2 class="body-2 ellipsis secondary--text mb-1" v-text="title"></h2>
        <slot></slot>
    </div>
</script>
<script>
    Vue.component('vc-empty-section-headline', {
        template: '#vc-empty-section-headline',
        props: ['title']
    });
</script>
<script type="text/x-template" id="vc-dialog">
    <v-dialog v-model="value.show" @input="onDialogClose"
              :fullscreen="fullscreen" :hide-overlay="fullscreen"
              :transition="fullscreen ? 'dialog-bottom-transition' : 'dialog-transition'"
              :width="width" lazy :persistent="persistent" scrollable>
        <v-fade-transition>
            <v-card v-if="value.show">
                <v-toolbar class="title grey lighten-3 elevation-0">
                    <v-btn v-if="fullscreen" flat @click="close" icon color="red">
                        <v-icon>close</v-icon>
                    </v-btn>
                    {{ value.title }}
                    <v-spacer></v-spacer>
                    <slot name="title-actions"></slot>
                    <v-btn v-if="!fullscreen" flat @click="close" icon color="red">
                        <v-icon>close</v-icon>
                    </v-btn>
                </v-toolbar>
                <slot></slot>
            </v-card>
        </v-fade-transition>
    </v-dialog>
</script>
<script>
    Vue.component('vc-dialog', {
        template: '#vc-dialog',
        /*value-interface: {show, title}*/
        props: {
            value: Object, width: Number,
            persistent: {
                type: Boolean,
                default: false
            },
            fullscreen: {
                type: Boolean,
                default: false
            }
        },
        data: function () {
            return {}
        },
        methods: {
            onDialogClose: function (event) {
                console.log('onDialogClose');
            },
            close: function () {
                console.log('close');
                this.value.show = false;
            }
        },
        mounted: function () {
            var _this = this;
            document.addEventListener("keydown", function (e) {
                if (_this.value.show && e.keyCode === 27) {
                    _this.close();
                }
            });
        }
    });
</script>
<script type="text/x-template" id="vc-modify-dialog">
    <vc-dialog ref="dialog" :persistent="true" v-model="value" :width="600">
        <v-card-text>
            <v-form ref="form" v-model="form.valid" lazy-validation>
                <v-container grid-list-md>
                    <v-layout wrap>
                        <slot name="content" :item="value.item"></slot>
                    </v-layout>
                </v-container>
            </v-form>
        </v-card-text>
        <v-divider></v-divider>
        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="primary" flat @click="close">
                Zamknij
            </v-btn>
            <v-btn color="primary" :disabled="!form.valid" flat @click="submit">
                Zapisz
            </v-btn>
        </v-card-actions>
    </vc-dialog>
</script>
<script>
    Vue.component('vc-modify-dialog', {
        template: '#vc-modify-dialog',
        /*value-interface: {show, title, item, url, onSuccess, onError, onChangeItem}*/
        props: ['value', 'initialObject'],
        data: function () {
            return {
                form: {
                    valid: false
                },
                prevObject: Object.assign({}, this.initialObject),
            }
        },
        mounted: function () {
        },
        computed: {
            item: function () {
                return this.value.item;
            }
        },
        watch: {
            item: {
                handler: function (newValue, oldValue) {
                    if (typeof this.value.onChangeItem === "function") {
                        this.value.onChangeItem(newValue, this.prevObject);
                    }
                    this.prevObject = JSON.parse(JSON.stringify(newValue));
                },
                deep: true
            }
        },
        methods: {
            submit: function () {
                var _this = this;

                if (this.$refs.form.validate()) {
                    this.$http.post(_this.value.url, _this.value.item)
                            .then(function (response) {
                                if (typeof _this.value.onSuccess === "function") {
                                    try {
                                        _this.value.onSuccess(response.data);
                                    }
                                    catch (e) {
                                        console.error(e);
                                    }
                                }
                                _this.close()
                            })
                            .catch(function (error) {
                                if (typeof _this.value.onError === "function") {
                                    try {
                                        _this.value.onError(error);
                                    }
                                    catch (e) {
                                        console.error(e);
                                    }
                                }
                                alert('Nie zapisano');
                            })
                            .then(function () {

                            });
                    console.log('valid');
                }
                else {
                    console.log('invalid');
                }
            },
            close: function () {
                this.$refs.dialog.close();
            }
        }
    });
</script>
<script type="text/x-template" id="vc-info-dialog">
    <vc-dialog ref="dialog" v-model="value" :width="1000">
        <v-card-text>
            <v-container grid-list-md>
                <v-layout wrap>
                    <slot name="content" :item="value.item"></slot>
                </v-layout>
            </v-container>
        </v-card-text>
        <v-divider></v-divider>
        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="primary" flat @click="close">
                Zamknij
            </v-btn>
        </v-card-actions>
    </vc-dialog>
</script>
<script>
    Vue.component('vc-info-dialog', {
        template: '#vc-info-dialog',
        /*value-interface: {show, title, item}*/
        props: ['value'],
        data: function () {
            return {}
        },
        methods: {
            close: function () {
                this.$refs.dialog.close();
            }
        }
    });
</script>
<script type="text/x-template" id="vc-confirm-dialog">
    <vc-dialog ref="dialog" v-model="value" :width="450">
        <v-card-text>
            <v-container grid-list-md>
                <v-layout wrap>
                    <slot></slot>
                </v-layout>
            </v-container>
        </v-card-text>
        <v-divider></v-divider>
        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="primary" flat @click="close">
                Nie
            </v-btn>
            <v-btn color="primary" flat @click="confirm">
                Tak
            </v-btn>
        </v-card-actions>
    </vc-dialog>
</script>
<script>
    Vue.component('vc-confirm-dialog', {
        template: '#vc-confirm-dialog',
        /*value-interface: {show, title, url, onSuccess, onError}*/
        props: ['value'],
        data: function () {
            return {}
        },
        methods: {
            confirm: function () {
                var _this = this;
                this.$http.post(_this.value.url)
                        .then(function (response) {
                            if (typeof _this.value.onSuccess === "function") {
                                try {
                                    _this.value.onSuccess(response.data);
                                }
                                catch (e) {
                                    console.error(e);
                                }
                            }
                        })
                        .catch(function (error) {
                            if (typeof _this.value.onError === "function") {
                                _this.value.onError(error);
                            }
                        })
                        .then(function () {
                            _this.close();
                        });
            },
            close: function () {
                this.$refs.dialog.close();
            }
        }
    });
</script>
<script type="text/x-template" id="vc-data-iterator">
    <div>
        <v-data-iterator
                :items="items" :rows-per-page-items="rowsPerPageItems"
                :pagination.sync="pagination" content-tag="v-list"
                :content-props="{'three-line': true}" row wrap
                hide-actions
                no-data-text="Brak danych"
                no-results-text="Brak danych"
                :total-items="totalElements"
        >
            <template slot="item" slot-scope="props">
                <v-list-tile xs12 @click.prevent="info(props.item)">
                    <slot name="item-content" :item="props.item"></slot>

                    <v-list-tile-action v-if="infoEnable" class="v-list__tile__action__hover">
                        <v-btn @click.stop="info(props.item)" icon ripple>
                            <v-icon color="grey darken-1">info</v-icon>
                        </v-btn>
                    </v-list-tile-action>

                    <v-list-tile-action v-if="editEnable" class="v-list__tile__action__hover">
                        <v-btn @click.stop="edit(props.item)" icon ripple>
                            <v-icon color="grey darken-1">edit</v-icon>
                        </v-btn>
                    </v-list-tile-action>

                    <v-list-tile-action v-if="removeEnable" class="v-list__tile__action__hover">
                        <v-btn @click.stop="remove(props.item)" icon ripple>
                            <v-icon color="grey darken-1">delete</v-icon>
                        </v-btn>
                    </v-list-tile-action>

                </v-list-tile>
                <v-divider v-if="props.index !== items.length - 1"></v-divider>
            </template>
        </v-data-iterator>
        <div class="text-xs-center pt-2">
            <v-pagination v-model="pagination.page" :length="totalPages"></v-pagination>
        </div>
    </div>
</script>
<script>
    Vue.component('vc-data-iterator', {
        template: '#vc-data-iterator',
        props: ['url', 'params', 'infoEnable', 'editEnable', 'removeEnable'],
        data: function () {
            return {
                items: [],
                totalElements: 0,
                totalPages: 0,
                rowsPerPageItems: [6],
                pagination: {}
            }
        },
        watch: {
            pagination: {
                handler: function handler() {
                    this.fetch();
                },
                deep: true
            }
        },
        methods: {
            refresh: function () {
                this.fetch();
            },
            edit: function (item) {
                this.$emit('edit', item);
            },
            remove: function (item) {
                this.$emit('remove', item);

            },
            info: function (item) {
                this.$emit('info', item);

            },
            fetch: function () {
                this.$vuetify.goTo(0, {duration: 0});
                var page = this.pagination.page;
                var size = this.pagination.rowsPerPage;
                var sort = 'id' || this.pagination.sortBy;
                var _this = this;

                var params = Object.assign({}, this.params, {
                    page: page - 1,
                    size: size,
                    sort: sort
                });

                this.$http.get(this.url, {params: params})
                        .then(function (response) {
                            _this.items = response.data.content;
                            _this.totalElements = response.data.totalElements;
                            _this.totalPages = response.data.totalPages;
                        });
            }
        }
    });
</script>
<script type="text/x-template" id="vc-data-management">
    <div>
        <vc-confirm-dialog v-model="confirmDialog">
            Potwierdź wykonanie operacji.
        </vc-confirm-dialog>

        <vc-info-dialog v-model="errorDialog">
            <template slot="content">
                Akcja mogła nie zostać wykonana. W razie problemów skonsultuj się z administatorem.
            </template>
        </vc-info-dialog>

        <vc-info-dialog v-model="infoDialog">
            <template slot="content" slot-scope="props">
                <slot name="info-dialog-content" :item="props.item"></slot>
            </template>
        </vc-info-dialog>

        <vc-data-iterator ref="vcDataIterator"
                          :url="iteratorDetails.url"
                          :params="iteratorDetails.params"
                          :infoEnable="infoDetails !== null" :editEnable="editDetails !== null"
                          :removeEnable="removeDetails !== null"
                          @info="info" @edit="edit" @remove="remove">
            <template slot="item-content" slot-scope="props">
                <slot name="data-iterator-content" :item="props.item"></slot>
            </template>
        </vc-data-iterator>

        <v-btn v-if="addDetails !== null" @click="add" fixed dark fab bottom right color="pink" style="bottom: 40px;">
            <v-icon>add</v-icon>
        </v-btn>

    </div>
</script>
<script>
    Vue.component('vc-data-management', {
        template: '#vc-data-management',
        props: {
            iteratorDetails: Object,
            infoDetails: {
                type: Object,
                default: function () {
                    return null;
                }
            },
            addDetails: {
                type: Object,
                default: function () {
                    return null;
                }
            },
            editDetails: {
                type: Object,
                default: function () {
                    return null;
                }
            },
            removeDetails: {
                type: Object,
                default: function () {
                    return null;
                }
            },
            initialObject: {
                type: Object,
                default: function () {
                    return {};
                }
            },
            onChangeItem: {
                type: Function,
                default: function () {
                    return null;
                }
            },
            customize: {
                type: Function,
                default: function (v) {
                    return v;
                }
            }
        },
        data: function () {
            return {
                confirmDialog: {
                    _this: this,
                    show: false,
                    onSuccess: function (data) {
                        console.log(data);
                        this._this.refresh();
                    },
                    onError: function (error) {
                        this._this.errorDialog.show = true;
                    }
                },
                modifyDialog: {
                    _this: this,
                    show: false,
                    item: Object.assign({}, this.initialObject),
                    onSuccess: function (data) {
                        console.log(data);
                        this._this.refresh();
                    },
                    onChangeItem: this.onChangeItem,
                    onError: function (error) {
                        this._this.errorDialog.show = true;
                    }
                },
                infoDialog: {
                    show: false,
                    item: {}
                },
                errorDialog: {
                    show: false,
                    title: 'Wystąpił błąd'
                }
            }
        },
        methods: {
            refresh: function () {
                this.$refs.vcDataIterator.refresh()
            },
            cleanObject: function (object) {
                var obj = Object.assign({}, object);
                for (var propName in obj) {
                    if (obj[propName] === null || obj[propName] === undefined) {
                        delete obj[propName];
                    }
                }
                return obj;
            },
            add: function () {
                var dialog = this.modifyDialog;
                dialog.url = this.addDetails.url;
                dialog.title = this.addDetails.title;
                dialog.item = JSON.parse(JSON.stringify(Object.assign({}, this.initialObject)));
                dialog.show = true;
            },
            edit: function (item) {
                var _this = this;
                var dialog = this.modifyDialog;
                dialog.url = this.editDetails.url;
                dialog.title = this.editDetails.title;
                dialog.item = JSON.parse(JSON.stringify(Object.assign({}, _this.initialObject)));
                this.$nextTick(function () {
                    dialog.item = this.customize(JSON.parse(JSON.stringify(Object.assign({}, _this.initialObject, _this.cleanObject(item)))));
                });
                dialog.show = true;
            },
            remove: function (item) {
                var dialog = this.confirmDialog;
                dialog.url = this.removeDetails.url + item.id;
                dialog.title = this.removeDetails.title;
                dialog.show = true;
            },
            info: function (item) {
                var dialog = this.infoDialog;
                dialog.title = this.infoDetails.title;
                dialog.item = Object.assign({}, item);
                dialog.show = true;
            }
        }
    });
</script>
<script type="text/x-template" id="vc-data-iterator-viewer">
    <div style="width: 100%;">
        <vc-info-dialog v-model="infoDialog">
            <template slot="content" slot-scope="props">
                <slot name="info-dialog-content" :item="props.item"></slot>
            </template>
        </vc-info-dialog>

        <v-flex
                v-for="(item, index) in items"
                :key="index"
                xs12
                class="mb-2 mt-3"
                v-if="showItem(item)"
        >
            <v-card>
                <v-card-title class="py-1 no-flex-wrap">
                    <slot name="item-title" :item="item"></slot>

                    <v-spacer></v-spacer>
                    <slot name="item-title-actions" :item="item" :index="index"></slot>
                    <v-btn v-if="infoBtn" @click.native="info(item)" icon>
                        <v-icon>info</v-icon>
                    </v-btn>
                </v-card-title>

                <v-divider></v-divider>
                <slot name="item-content" :item="item"></slot>
            </v-card>
        </v-flex>

        <v-layout align-center>
            <v-flex text-xs-center>
                <v-btn v-if="!last" @click.native="loadNextPage" :loading="loading" flat color="primary">
                    Załaduj więcej
                </v-btn>
                <p v-else class="text-md-center">Koniec wyników</p>
            </v-flex>
        </v-layout>
    </div>
</script>
<script>
    Vue.component('vc-data-iterator-viewer', {
        template: '#vc-data-iterator-viewer',
        props: {
            value: Object,
            infoBtn: {
                type: Boolean,
                default: true
            },
            showItem: {
                type: Function,
                default: function (item) {
                    return true;
                }
            }
        },
        data: function () {
            return {
                infoDialog: {
                    title: 'Wiecej informacji o wizycie',
                    show: false,
                    item: {}
                },
                items: [],
                totalElements: 0,
                totalPages: 0,
                loading: false,
                last: false,
                pagination: {
                    page: 1,
                    rowsPerPage: 3
                }
            }
        },
        mounted: function () {
            this.fetch();
        },
        methods: {
            info: function (item) {
                this.infoDialog.item = item;
                this.infoDialog.show = true;
            },
            refresh: function () {
                this.fetch();
            },
            loadNextPage: function () {
                this.pagination.page++;
                this.fetch();
            },
            fetch: function () {
                var page = this.pagination.page;
                var size = this.pagination.rowsPerPage;
                var sort = this.value.sortBy || 'id';
                var _this = this;

                _this.loading = true;

                var params = {
                    page: page - 1,
                    size: size,
                    sort: sort
                };
                params = Object.assign({}, params, this.value.params);

                this.$http.get(this.value.url, {params: params})
                        .then(function (response) {
                            console.log(response);
                            _this.items = _this.items.concat(response.data.content);
                            _this.last = response.data.last;
                            _this.totalElements = response.data.totalElements;
                            _this.totalPages = response.data.totalPages;
                        })
                        .catch(function (error) {
                            console.log(error);
                        })
                        .then(function () {
                            _this.loading = false;
                        });
            }
        }
    });
</script>
<script type="text/x-template" id="vc-date-time-picker">
    <v-flex d-flex class="pa-0 mt-2">
        <v-menu class="pr-1"
                :close-on-content-click="false"
                v-model="menuDropdown"
                lazy
                offset-y
                full-width
                max-width="290px"
                min-width="290px"
        >
            <v-text-field
                    solo
                    slot="activator"
                    :label="dateLabel"
                    persistent-hint
                    v-model="dateFormatted"
                    :rules="[$rules.required, $rules.date]"
                    @change="parseDate(dateFormatted)"
            ></v-text-field>
            <v-date-picker locale="pl-pl" v-model="date" no-title @input="menuDropdown = false"></v-date-picker>
        </v-menu>

        <v-combobox
                solo
                class="v-list__tile__small"
                v-model="time"
                :items="items"
                :append-icon="null"
                :menu-props="{maxHeight:'200px'}"
                :label="timeLabel"
                :rules="[$rules.required, $rules.hour]"
        ></v-combobox>
    </v-flex>
</script>
<script>
    Vue.component('vc-date-time-picker', {
        template: '#vc-date-time-picker',
        props: ['date-time', 'dateLabel', 'timeLabel'],
        data: function () {
            return {
                dateAndTime: '',
                date: '',
                dateFormatted: '',
                time: '',
                menuDropdown: false
            }
        },
        computed: {
            items: function () {
                var interval = 30;
                var times = [];
                var tt = 0;
                for (var i = 0; tt < 24 * 60; i++) {
                    var hh = Math.floor(tt / 60);
                    var mm = (tt % 60);
                    times[i] = ("0" + (hh % 24)).slice(-2) + ':' + ("0" + mm).slice(-2);
                    tt = tt + interval;
                }
                return times;
            }
        },
        watch: {
            dateTime: function (val) {
                this.dateAndTime = val;
                var dateAndTime = moment(this.dateAndTime, 'YYYY-MM-DD HH:mm', true);
                if (dateAndTime.isValid()) {
                    this.date = dateAndTime.format('YYYY-MM-DD');
                    this.time = dateAndTime.format('HH:mm');
                }
                else {
                    this.date = '';
                    this.time = '';

                }
            },
            date: function (val) {
                this.dateFormatted = this.formatDate(this.date);
                this.dateAndTime = this.date + ' ' + this.time;
            },
            time: function (val) {
                this.dateAndTime = this.date + ' ' + this.time;
            },
            dateAndTime: function (val) {
                if (this.$moment(val, 'YYYY-MM-DD HH:mm', true).isValid())
                    this.$emit('date-time-change', val);
            }
        },
        methods: {
            formatDate: function formatDate(date) {
                if (!date) return null;

                var dateSplit = date.split('-');
                var year = dateSplit[0], month = dateSplit[1], day = dateSplit[2];

                return day + '.' + month + '.' + year;
            },
            parseDate: function parseDate(date) {
                if (!date) {
                    this.date = '';
                    return;
                }

                var dateSplit = date.split('.');
                var year = dateSplit[2], month = dateSplit[1], day = dateSplit[0];



                date = year + '-' + month.padStart(2, '0') + '-' + day.padStart(2, '0');

                if (moment(date, 'YYYY-MM-DD', true).isValid()) {
                    this.date = date;
                }
                else {
                    this.dateFormatted = this.formatDate(this.date);
                }
            }

        }

    });
</script>
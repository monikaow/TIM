<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<#assign spring=JspTaglibs["http://www.springframework.org/tags"]>

<script type="text/x-template" id="vc-doctor-manage-visits">
    <div>
        <vc-info-dialog v-model="recipeHistoryDialog">
            <template slot="content" slot-scope="props">
                <vc-data-iterator-viewer v-model="iteratorDetails" :infoBtn="false">

                    <template slot="info-dialog-content" slot-scope="props">
                        {{props.item}}
                    </template>

                    <template slot="item-title" slot-scope="props">
                        <div class="body-1 one-line-block-center my-2">
                            <v-icon class="mr-2">person</v-icon>
                            <b>
                                {{props.item.medicalVisit.doctorSpecialization.doctor.title}}
                                {{props.item.medicalVisit.doctorSpecialization.doctor.firstName}}
                                {{props.item.medicalVisit.doctorSpecialization.doctor.lastName}}
                                &nbsp;</b>
                        </div>
                        <v-divider class="mx-2" vertical></v-divider>
                        <div class="body-1 one-line-block-center my-2">
                            <v-icon class="mr-2">access_time</v-icon>
                            <b>{{ fullDate(props.item.medicalVisit.doctorTimetable.date)}}&nbsp;</b>
                        </div>
                    </template>

                    <template slot="item-content" slot-scope="props">
                        <v-card-text class="px-3">

                            <div class="body-1 one-line-block-center mb-2">
                                <v-icon class="mr-2">subject</v-icon>
                                <div><b>Lek:&nbsp;</b>{{ props.item.medicineName }}</div>
                            </div>
                            <div class="body-1 one-line-block-center mb-2">
                                <v-icon class="mr-2">info</v-icon>
                                <div><b>Komentarz:&nbsp;</b>{{ props.item.comment }}</div>
                            </div>
                            <div class="body-1 one-line-block-center mb-2">
                                <v-icon class="mr-2">access_time</v-icon>
                                <div><b>Do odbioru:&nbsp;</b>{{ fullDate(props.item.receiptDate) }}</div>
                            </div>
                        </v-card-text>
                    </template>
                </vc-data-iterator-viewer>
            </template>
        </vc-info-dialog>

        <vc-modify-dialog v-model="editRecipeHistoryDialog">
            <template slot="content" slot-scope="props">
                <v-layout row>
                    <v-flex xs4>
                        <v-subheader>Lek:</v-subheader>
                    </v-flex>
                    <v-flex xs8>
                        <v-text-field v-model="props.item.medicineName" :rules="[$rules.required]" solo></v-text-field>
                    </v-flex>
                </v-layout>
                <v-layout row>
                    <v-flex xs4>
                        <v-subheader>Komentarz:</v-subheader>
                    </v-flex>
                    <v-flex xs8>
                        <v-text-field v-model="props.item.comment" :rules="[$rules.required]" solo></v-text-field>
                    </v-flex>
                </v-layout>
                <v-layout row>
                    <v-flex xs4>
                        <v-subheader>Do odbioru:</v-subheader>
                    </v-flex>
                    <v-flex xs8>
                        <vc-date-time-picker :date-time="props.item.receiptDate" dateLabel="" timeLabel=""
                                             @date-time-change="props.item.receiptDate = $event"></vc-date-time-picker>
                    </v-flex>
                </v-layout>
            </template>
        </vc-modify-dialog>

        <vc-section-headline title="Wizyty">
            <v-date-picker
                    v-model="pickedDate"
                    full-width
                    no-title
                    locale="pl"
                    class="mt-2"
                    first-day-of-week="1"
            ></v-date-picker>
            <v-layout align-center justify-center row fill-height class="mt-3">
                <div class="display-3 font-weight-light mr-3">{{ date.format('D') }}</div>
                <div>
                    <div class="headline font-weight-light">{{ date.format('dddd') }}</div>
                    <div class="text-uppercase font-weight-light">{{ date.format('MMMM YYYY') }}</div>
                </div>
            </v-layout>
            <v-card-text class="py-2">
                <v-timeline dense>
                    <v-timeline-item
                            v-for="(item, index) in items"
                            :key="index"
                            color="pink"
                            small
                    >
                        <v-layout align-center justify-space-between row fill-height>
                            <v-flex xs3 class="mr-3">
                                <strong>{{ time(item.doctorTimetable.date) }}</strong>
                            </v-flex>
                            <v-flex style="min-height: 48px;">
                                <strong>{{ item.patient.firstName + ' ' + item.patient.lastName }}</strong>
                                <div class="caption">{{ item.doctorSpecialization.specialization.name }}</div>
                            </v-flex>
                            <div>
                                <v-btn @click.stop="editHealthHistory(item.id)" icon ripple>
                                    <v-icon color="grey darken-1">edit</v-icon>
                                </v-btn>
                            </div>
                            <div>
                                <v-btn @click.stop="healthHistory(item.patient.id)" icon ripple>
                                    <v-icon color="grey darken-1">list_alt</v-icon>
                                </v-btn>
                            </div>
                        </v-layout>
                    </v-timeline-item>
                </v-timeline>
            </v-card-text>
        </vc-section-headline>
    </div>
</script>
<script>
    Vue.component('vc-doctor-manage-visits', {
        template: '#vc-doctor-manage-visits',
        props: [],
        mixins: [timeMixin],
        data: function () {
            return {
                pickedDate: '',
                items: [],

                recipeHistoryDialog: {
                    show: false,
                    title: 'Historia recept'
                },
                editRecipeHistoryDialog: {
                    show: false,
                    title: 'Dodaj Receptę',
                    url: '/api/doctor/register/recipe-history',
                    item: {
                        medicalVisitId: null,
                        medicineName: '',
                        comment: '',
                        receiptDate: ''

                    },
                    initialObject: {
                        medicineName: '',
                        comment: '',
                        receiptDate: ''
                    }
                },
                iteratorDetails: {
                    url: '/api/patient/recipe-history/fetch',
                    sortBy: 'receiptDate,desc',
                    params: {
                        patientId: null
                    }
                }
            }
        },
        methods: {
            fetch: function () {
                var _this = this;
                this.$http.get('/api/doctor/visits/fetch',
                        {
                            params: {
                                doctorId: <@security.authentication property="principal.id"/>,
                                date: this.pickedDate
                            }
                        })
                        .then(function (response) {
                            _this.items = response.data;
                        })
                        .catch()
                        .then();
            },
            healthHistory: function (patientId) {
                this.iteratorDetails.params.patientId = patientId;
                this.recipeHistoryDialog.show = true;
            },
            editHealthHistory: function (medicalVisitId) {
                var editRecipeHistoryDialog = this.editRecipeHistoryDialog;

                editRecipeHistoryDialog.item = JSON.parse(JSON.stringify(Object.assign({}, editRecipeHistoryDialog.item, editRecipeHistoryDialog.initialObject)))
                editRecipeHistoryDialog.item.medicalVisitId = medicalVisitId;

                editRecipeHistoryDialog.show = true;
            }
        },
        watch: {
            pickedDate: function (v) {
                this.fetch();
            }
        },
        computed: {
            date: function () {
                return this.$moment(this.pickedDate, 'YYYY-MM-DD', true);
            }
        },
        mounted: function () {
            this.pickedDate = this.$moment().format('YYYY-MM-DD');
        }
    });
</script>
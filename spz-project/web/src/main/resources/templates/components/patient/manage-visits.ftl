<#assign spring=JspTaglibs["http://www.springframework.org/tags"]>
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />

<script type="text/x-template" id="vc-patient-manage-visits">
    <div>
        <vc-confirm-dialog v-model="confirmDialog">
            Potwierdź anulowanie. Tej operacji nie można cofnąć.
        </vc-confirm-dialog>

        <vc-empty-section-headline title="Wizyty">
            <vc-data-iterator-viewer v-model="iteratorDetails" :showItem="showItem">

                <template slot="info-dialog-content" slot-scope="props">
                    {{props.item}}
                </template>

                <template slot="item-title" slot-scope="props">
                    <strong>
                        {{props.item.doctorSpecialization.doctor.title}}
                        {{props.item.doctorSpecialization.doctor.firstName}}
                        {{props.item.doctorSpecialization.doctor.lastName}}
                    </strong>
                </template>

                <template slot="item-title-actions" slot-scope="props">
                    <v-btn v-if="isAfter(props.item.doctorTimetable.date)" @click.native="cancel(props.item, props.index)" icon>
                        <v-icon>cancel</v-icon>
                    </v-btn>
                </template>

                <template slot="item-content" slot-scope="props">
                    <v-card-text class="px-3">
                        <div class="body-1 one-line-block-center mb-2">
                            <v-icon class="mr-2">access_time</v-icon>
                            <b>{{ formatDate(props.item.doctorTimetable.date)}}&nbsp;</b>
                        </div>

                        <div class="body-1 one-line-block-top">
                            <v-icon class="mr-2">subject</v-icon>
                            {{ props.item.doctorSpecialization.specialization.name }}&nbsp;
                        </div>
                    </v-card-text>
                </template>
            </vc-data-iterator-viewer>
        </vc-empty-section-headline>
    </div>
</script>
<script>
    Vue.component('vc-patient-manage-visits', {
        template: '#vc-patient-manage-visits',
        props: [],
        data: function () {
            return {
                iteratorDetails: {
                    url: '/api/patient/visits/fetch',
                    sortBy: 'doctorTimetable.date,desc',
                    params: {
                        patientId: <@security.authentication property="principal.id"/>
                    }
                },
                infoDetails: {
                    title: 'Szczegóły wizyty'
                },
                confirmDialog: {
                    _this: this,
                    show: false,
                    url: '',
                    params: {
                        medicalVisitId: null
                    },
                    title: 'Anulowanie wizyty',
                    onSuccess: function (data) {
                        console.log(data);
                    },
                    onError: function (error) {
                        this._this.errorDialog.show = true;
                    }
                },
            }
        },
        methods: {
            formatDate: function (date) {
                var momentDate = this.$moment(date, 'YYYY-MM-DD HH:mm', true);
                return momentDate.format('D MMMM YYYY HH:mm');
            },
            isAfter: function (date) {
                var momentDate = this.$moment(date, 'YYYY-MM-DD HH:mm', true);
                return momentDate.isSameOrAfter(this.$moment());

            },
            cancel(item, index) {
                var _this = this;
                var dialog = this.confirmDialog;
                dialog.url = '/api/patient/visit/cancel?medicalVisitId=' + item.id;
                dialog.onSuccess = function (data) {
                    item.cancel = data.cancel;
                };

                dialog.show = true;
            },
            showItem: function (item) {
                return !item.cancel;
            }
        }
    });
</script>
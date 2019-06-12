<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<#assign spring=JspTaglibs["http://www.springframework.org/tags"]>

<script type="text/x-template" id="vc-patient-recipe-history">
    <div>
        <vc-empty-section-headline title="Przepisane Recepty">
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
        </vc-empty-section-headline>
    </div>
</script>
<script>
    Vue.component('vc-patient-recipe-history', {
        template: '#vc-patient-recipe-history',
        props: [],
        mixins: [timeMixin],
        data: function () {
            return {
                iteratorDetails: {
                    url: '/api/patient/recipe-history/fetch',
                    sortBy: 'receiptDate,desc',
                    params: {
                        patientId: <@security.authentication property="principal.id"/>
                    }
                }
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
            }
        }
    });
</script>
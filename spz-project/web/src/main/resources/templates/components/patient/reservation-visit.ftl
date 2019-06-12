<#assign spring=JspTaglibs["http://www.springframework.org/tags"]>
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />

<script type="text/x-template" id="vc-doctor-timetable">
    <v-flex xs12>
        <v-card :class="(chosenDoctorTimetable && (chosenDoctorTimetable.doctorSpecialization.doctor.id === doctorSpecialization.doctor.id)) ? 'card-active' : ''">
            <v-card-text>
                <v-layout row wrap>
                    <v-flex xs5 style="margin: auto;">
                        <div class="headline">{{ doctorSpecialization.doctor.title }} {{
                            doctorSpecialization.doctor.firstName }} {{ doctorSpecialization.doctor.lastName }}
                        </div>
                        <div class="grey--text">
                            <div>
                                {{ specializations(doctorSpecialization.doctor.specializations) }}
                            </div>
                            <div>konsultacje - od <span
                                    v-html="$options.filters.money(doctorSpecialization.doctor.price)"></span>
                            </div>
                            <div>Najbliższy termin: {{ closestDate ? fullDate(closestDate.date) : 'brak terminu' }}
                            </div>
                        </div>
                    </v-flex>
                    <v-flex xs7>
                        <v-container class="pa-0" grid-list-md text-xs-center>
                            <v-layout row wrap>
                                <v-flex xs1>
                                    <v-btn @click="prev" flat icon>
                                        <v-icon>chevron_left</v-icon>
                                    </v-btn>
                                </v-flex>
                                <v-flex v-for="(d, i) in dates" :key="i" xs3>
                                    <v-btn disabled flat><strong style="color: black !important;">{{ d }}</strong>
                                    </v-btn>
                                </v-flex>
                                <v-flex xs1>
                                    <v-btn @click="next" flat icon>
                                        <v-icon>chevron_right</v-icon>
                                    </v-btn>
                                </v-flex>
                            </v-layout>
                            <v-layout row wrap style="overflow-y: auto; max-height: 400px;">
                                <v-flex xs1></v-flex>

                                <v-flex v-for="(doctorTimetables, dtsIndex) in doctorTimetablesList" :key="dtsIndex"
                                        xs3>
                                    <v-btn v-for="(doctorTimetable, dtIndex) in doctorTimetables" :key="dtIndex"
                                           flat color="success" @click="chooseDoctorTimetable(doctorTimetable)"
                                           :input-value="chosenDoctorTimetable && (chosenDoctorTimetable.id === doctorTimetable.id)"
                                    >
                                        {{ time(doctorTimetable.date) }}
                                    </v-btn>
                                </v-flex>
                                <v-flex xs1></v-flex>
                            </v-layout>
                        </v-container>
                    </v-flex>
                </v-layout>
            </v-card-text>
        </v-card>
    </v-flex>
</script>
<script>
    Vue.component('vc-doctor-timetable', {
        template: '#vc-doctor-timetable',
        props: ['doctorSpecialization', 'chosenDoctorTimetable'],
        mixins: [timeMixin],
        data: function () {
            return {
                NUMBER_OF_DATES: 3,
                date: '',
                doctorTimetablesData: null,
                closestDate: null
            }
        },
        methods: {
            next: function () {
                this.date = this.$moment(this.date, 'YYYY-MM-DD', true).add(this.NUMBER_OF_DATES, 'days').format('YYYY-MM-DD');
                this.fetch();
            },
            prev: function () {
                this.date = this.$moment(this.date, 'YYYY-MM-DD', true).subtract(this.NUMBER_OF_DATES, 'days').format('YYYY-MM-DD');
                this.fetch();
            },
            fetch: function () {
                var _this = this;
                var doctorId = this.doctorSpecialization.doctor.id;
                this.$http.get('/api/doctor/timetable/fetch', {
                    params: {
                        doctorId: doctorId,
                        dates: this.getDates(this.date).join(', ')
                    }
                }).then(function (response) {
                    _this.doctorTimetablesData = response.data;
                })
            },
            fetchClosestDate: function () {
                var _this = this;
                var doctorId = this.doctorSpecialization.doctor.id;
                this.$http.get('/api/doctor/timetable/first', {
                    params: {
                        doctorId: doctorId
                    }
                }).then(function (response) {
                    _this.closestDate = response.data;
                })
            },
            getDates: function (startDate, format) {
                format = format ? format : 'YYYY-MM-DD';
                var momentDate = this.$moment(startDate, 'YYYY-MM-DD', true);

                var dates = [];
                for (var i = 0; i < this.NUMBER_OF_DATES; i++) {
                    dates.push(momentDate.format(format));
                    momentDate = momentDate.add(1, 'days');
                }

                return dates;
            },
            specializations: function (specializations) {
                return specializations.map(
                        function (s) {
                            return s.name;
                        }
                ).join(', ')
            },
            chooseDoctorTimetable: function (doctorTimetable) {
                doctorTimetable.doctorSpecialization = this.doctorSpecialization;
                this.$emit('chooseDoctorTimetable', doctorTimetable);
            }
        },
        computed: {
            dates: function () {
                return this.getDates(this.date, 'D MMM');
            },
            doctorTimetablesList: function () {
                if (this.doctorTimetablesData && (this.doctorTimetablesData.length === this.NUMBER_OF_DATES))
                    return this.doctorTimetablesData;

                var list = [];
                for (var i = 0; i < this.NUMBER_OF_DATES; i++) {
                    list.push([]);
                }
                return list;
            }
        },
        mounted: function () {
            this.date = this.$moment().format('YYYY-MM-DD');
            this.fetch();
            this.fetchClosestDate();
        }
    });
</script>
<style>
    .v-btn--active:before {
        border: 3px solid black;
    }

    .card-active {
        background-color: rgba(255, 222, 61, 0.14) !important;
    }
</style>
<script type="text/x-template" id="vc-patient-reservation-visit">
    <vc-empty-section-headline title="Zarezerwuj wizytę">
        <v-stepper v-if="!complete" v-model="stepper" vertical>
            <v-stepper-step :complete="stepper > 1" step="1">
                Jakiego specjalisty szukasz? {{ specialization ? specialization.name : '' }}
            </v-stepper-step>

            <v-stepper-content step="1">
                <v-container grid-list-md class="pa-1">
                    <v-layout row wrap>
                        <v-flex xs4>
                            <v-subheader>Specjalizacja:</v-subheader>
                        </v-flex>
                        <v-flex xs8>
                            <v-autocomplete
                                    v-model="specialization"
                                    :item-text="v => v.name"
                                    :item-value="v => v"
                                    :items="specializations"
                                    solo
                            ></v-autocomplete>
                        </v-flex>
                    </v-layout>
                </v-container>

                <v-btn color="primary" :disabled="!specialization" @click="stepper = 2">Dalej</v-btn>
            </v-stepper-content>

            <v-stepper-step :complete="stepper > 2" step="2">Wybierz lekarza. Wybrano: {{ doctorSpecializations.length
                }}
            </v-stepper-step>

            <v-stepper-content step="2">
                <v-container grid-list-md class="pa-1">
                    <v-layout row wrap>
                        <v-flex xs12>
                            <v-subheader>Pozostaw pole puste aby szukać terminów u wszystkich specjalistów.
                            </v-subheader>
                        </v-flex>
                        <v-flex xs4>
                            <v-subheader>Lekarz:</v-subheader>
                        </v-flex>
                        <v-flex xs8>
                            <v-autocomplete
                                    v-model="doctorSpecializations"
                                    :items="doctors"
                                    multiple
                                    :item-value="(v) => v"
                                    :item-text="doctorName"
                                    solo
                                    clearable
                            >
                                <template slot="selection" slot-scope="props">
                                    <v-chip color="primary" text-color="white">
                                        {{ props.item.doctor.title + ' ' + props.item.doctor.firstName + ' ' +
                                        props.item.doctor.lastName }}
                                    </v-chip>
                                </template>
                            </v-autocomplete>
                        </v-flex>
                    </v-layout>
                </v-container>
                <v-btn color="primary" @click="stepper = 3">Dalej</v-btn>
                <v-btn flat @click="stepper = 1">Wstecz</v-btn>
            </v-stepper-content>

            <v-stepper-step :complete="stepper > 3" step="3">Wolne terminy {{ doctorTimetableInfo(doctorTimetable) }}
            </v-stepper-step>

            <v-stepper-content step="3">
                <v-container grid-list-md class="pa-1">
                    <v-layout v-for="(chosenDoctor, index) in chosenDoctors" :key="index" row wrap>
                        <vc-doctor-timetable :doctorSpecialization="chosenDoctor"
                                             :chosenDoctorTimetable="doctorTimetable"
                                             @chooseDoctorTimetable="doctorTimetable = $event"
                        ></vc-doctor-timetable>
                    </v-layout>
                </v-container>
                <v-btn color="primary" :disabled="!doctorTimetable" @click="stepper = 4">Dalej</v-btn>
                <v-btn flat @click="stepper = 2">Wstecz</v-btn>
            </v-stepper-content>

            <v-stepper-step step="4">Podsumowanie</v-stepper-step>
            <v-stepper-content step="4">
                <v-container grid-list-md class="pa-1">
                    <v-layout row wrap>
                        <div v-if="doctorTimetable" class="v-table__overflow">
                            <table class="v-table">
                                <tbody>
                                <tr>
                                    <td><strong>Doktor</strong></td>
                                    <td>{{ doctorTimetable.doctorSpecialization.doctor.title }}
                                        {{ doctorTimetable.doctorSpecialization.doctor.firstName }}
                                        {{ doctorTimetable.doctorSpecialization.doctor.lastName }}
                                    </td>
                                </tr>
                                <tr>
                                    <td><strong>Data</strong></td>
                                    <td>{{ fullDate(doctorTimetable.date) }}
                                    </td>
                                </tr>


                                </tbody>
                            </table>
                        </div>
                    </v-layout>
                </v-container>
                <v-btn @click="save" :disabled="loading" :loading="loading" color="primary">Potwierdź</v-btn>
                <v-btn flat @click="stepper = 3">Wstecz</v-btn>
            </v-stepper-content>
        </v-stepper>
        <v-card v-else>
            <v-card-text class="pa-3 text-xs-center">
                <span class="d-inline-flex green--text darken-2 mb-3" style="border: 2px solid currentColor; border-radius: 50%; width: 70px; height: 70px;">
                    <v-icon x-large color="green darken-2">check</v-icon>
                </span>
                <h3 class="title font-weight-light mb-2">Zapisaliśmy Twoją wizytę </h3>
                <span class="body-1 grey--text">Szczegóły znajdziesz w wiadomości email</span>
            </v-card-text>
        </v-card>
    </vc-empty-section-headline>
</script>
<script>
    Vue.component('vc-patient-reservation-visit', {
        template: '#vc-patient-reservation-visit',
        props: [],
        mixins: [timeMixin],
        data: function () {
            return {
                complete: false,
                stepper: 1,
                specializations: ${JSONFactory.stringify(specializations)},
                doctorSpecializations: [],
                doctors: [],
                specialization: null,

                doctorTimetable: null,
                loading: false
            }
        },
        methods: {
            doctorName: function (doctorSpecialization) {
                if (!doctorSpecialization.doctor) return '';
                return doctorSpecialization.doctor.title + ' ' + doctorSpecialization.doctor.firstName + ' ' + doctorSpecialization.doctor.lastName;
            },
            doctorTimetableInfo: function (doctorTimetable) {
                if (!doctorTimetable) return '';
                var doctor = doctorTimetable.doctorSpecialization.doctor;
                return doctor.title + ' ' + doctor.firstName + ' ' + doctor.lastName + ' - ' + this.fullDate(doctorTimetable.date);

            },
            save: function () {
                var _this = this;
                this.loading = true;
                this.$http.post('/api/patient/register/visit', {
                    patientId: <@security.authentication property="principal.id"/>,
                    doctorSpecializationId: this.doctorTimetable.doctorSpecialization.id,
                    doctorTimetableId: this.doctorTimetable.id
                }).then(function (response) {
                    _this.complete = true;
                });
            }
        },
        watch: {
            specialization: function (val) {
                this.doctorSpecializations = [];
                var _this = this;
                this.$http.get('/api/doctor/specialization', {
                    params: {
                        specializationId: val.id
                    }
                }).then(function (response) {
                    _this.doctors = response.data;
                });
            }
        },
        computed: {
            chosenDoctors: function () {
                if (this.stepper < 3) {
                    this.doctorTimetable = null;
                    return [];
                }

                if (this.doctorSpecializations.length === 0) return this.doctors;

                return this.doctorSpecializations;
            }
        }

    });
</script>
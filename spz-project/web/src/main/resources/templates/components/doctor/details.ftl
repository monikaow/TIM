<#assign spring=JspTaglibs["http://www.springframework.org/tags"]>

<script type="text/x-template" id="vc-doctor-details">
    <vc-section-header title="Uzupełnij dane">

        <v-form v-model="form.valid" ref="form" lazy-validation>
            <v-container grid-list-md>
                <v-layout row>
                    <v-flex xs4>
                        <v-subheader>Tytuł, imię i nazwisko:</v-subheader>
                    </v-flex>
                    <v-flex xs2>
                        <v-text-field v-model="patient.title" :rules="[$rules.required]" solo></v-text-field>
                    </v-flex>
                    <v-flex xs3>
                        <v-text-field v-model="patient.firstName" :rules="[$rules.required]" solo></v-text-field>
                    </v-flex>
                    <v-flex xs3>
                        <v-text-field v-model="patient.lastName" :rules="[$rules.required]" solo></v-text-field>
                    </v-flex>
                </v-layout>
                <v-layout row>
                    <v-flex xs4>
                        <v-subheader>PESEL:</v-subheader>
                    </v-flex>
                    <v-flex xs8>
                        <v-text-field v-model="patient.nationalId" :rules="[$rules.required]" solo></v-text-field>
                    </v-flex>
                </v-layout>
                <v-layout row>
                    <v-flex xs4>
                        <v-subheader>Numer dowodu osobistego:</v-subheader>
                    </v-flex>
                    <v-flex xs8>
                        <v-text-field v-model="patient.idCardNo" :rules="[$rules.required]" solo></v-text-field>
                    </v-flex>
                </v-layout>

                <v-layout row>
                    <v-flex xs4>
                        <v-subheader>Adres:</v-subheader>
                    </v-flex>
                    <v-flex xs8>
                        <v-text-field v-model="patient.address" :rules="[$rules.required]" solo></v-text-field>
                    </v-flex>
                </v-layout>

                <v-layout row>
                    <v-flex xs4>
                        <v-subheader>Kod pocztowy i miejscowość:</v-subheader>
                    </v-flex>
                    <v-flex xs3>
                        <v-text-field v-model="patient.postalCode" :rules="[$rules.required]" solo></v-text-field>
                    </v-flex>
                    <v-flex xs5>
                        <v-text-field v-model="patient.city" :rules="[$rules.required]" solo></v-text-field>
                    </v-flex>
                </v-layout>

                <v-layout row>
                    <v-flex xs4>
                        <v-subheader>Telefon:</v-subheader>
                    </v-flex>
                    <v-flex xs8>
                        <v-text-field v-model="patient.mobileNumber" :rules="[$rules.required]" solo></v-text-field>
                    </v-flex>
                </v-layout>

                <v-layout wrap>
                    <v-flex xs12>
                        <v-card-actions>
                            <v-spacer></v-spacer>
                            <v-btn
                                    @click.stop="submit"
                                    color="primary">
                                Zapisz
                            </v-btn>
                        </v-card-actions>
                    </v-flex>
                </v-layout>
            </v-container>
        </v-form>
    </vc-section-header>
</script>
<script>
    Vue.component('vc-doctor-details', {
        template: '#vc-doctor-details',
        props: [],
        data: function () {
            return {
                form: {
                    valid: false
                },
                patient: ${JSONFactory.stringify(doctor)}
            }
        },
        methods: {
            submit: function () {
                if (this.$refs.form.validate()) {
                    var _this = this;

                    this.$http.post('/api/doctor/details', _this.patient)
                            .then(function (response) {
                                // handle success
                                window.alert('Zapisano');
                            })
                            .catch(function (error) {
                                // handle error
                                window.alert('Nie zapisano');
                            });
                }
            }
        }
    });
</script>
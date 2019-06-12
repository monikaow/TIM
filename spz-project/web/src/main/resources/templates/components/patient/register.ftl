<#assign spring=JspTaglibs["http://www.springframework.org/tags"]>

<script type="text/x-template" id="vc-patient-register">

    <vc-section-header title="Rejestracja pacjenta">

        <v-container v-if="!!alert.message" grid-list-md>
            <v-alert :value="true"
                     type="error"
                     icon="new_releases"
            >
                {{ alert.message }}
            </v-alert>
        </v-container>
        <v-container grid-list-md>
            <v-form v-model="form.valid" ref="form" lazy-validation>


                <v-layout row>
                    <v-flex xs4>
                        <v-subheader>Imię i nazwisko:</v-subheader>
                    </v-flex>
                    <v-flex xs4>
                        <v-text-field v-model="patient.firstName" :rules="[$rules.required]" solo></v-text-field>
                    </v-flex>
                    <v-flex xs4>
                        <v-text-field v-model="patient.lastName" :rules="[$rules.required]" solo></v-text-field>
                    </v-flex>
                </v-layout>

                <v-layout row>
                    <v-flex xs4>
                        <v-subheader>E-mail:</v-subheader>
                    </v-flex>
                    <v-flex xs8>
                        <v-text-field solo :rules="[$rules.required]"
                                      v-model="patient.email"
                        ></v-text-field>
                    </v-flex>
                </v-layout>

                <v-layout row>
                    <v-flex xs4>
                        <v-subheader>Hasło:</v-subheader>
                    </v-flex>
                    <v-flex xs8>
                        <v-text-field
                                solo
                                v-model="patient.password"
                                :append-icon="showPassword ? 'visibility_off' : 'visibility'"
                                :type="showPassword ? 'text' : 'password'"
                                :rules="[$rules.required]"
                                name="password"
                                @click:append="showPassword = !showPassword"
                        ></v-text-field>
                    </v-flex>
                </v-layout>
                <v-layout row>
                    <v-flex xs12>
                        <v-subheader>
                            <v-checkbox
                                    v-model="patient.acceptTerms"
                                    :rules="[$rules.required]"
                                    color="green"
                            >
                                <div slot="label">
                                    <span @click="patient.acceptTerms = !patient.acceptTerms">
                                        Akceptuje
                                        <a href="javascript:;">regulamin i warunki</a>
                                        korzystania z serwisu.
                                    </span>
                                </div>
                            </v-checkbox>

                        </v-subheader>
                    </v-flex>
                </v-layout>

                <v-layout wrap>
                    <v-flex xs12>
                        <v-card-actions>
                            <v-spacer></v-spacer>
                            <v-btn
                                    @click="submit"
                                    color="primary">
                                Zarejestruj się
                            </v-btn>
                        </v-card-actions>
                    </v-flex>
                </v-layout>
            </v-form>
        </v-container>

    </vc-section-header>
</script>
<script>
    Vue.component('vc-patient-register', {
        template: '#vc-patient-register',
        props: [],
        data: function () {
            return {
                alert: {
                    message: ''
                },
                form: {
                    valid: true
                },
                patient: {
                    firstName: '',
                    lastName: '',
                    email: '',
                    password: '',
                    acceptTerms: false
                },
                showPassword: false
            }
        },
        methods: {
            submit: function () {
                this.alert.message = '';
                if (this.$refs.form.validate()) {
                    var _this = this;
                    this.$http.post('/auth/patient/register', _this.patient)
                            .then(function (response) {
                                redirect('/patient/details')
                                // handle success
                                console.log(response);
                            })
                            .catch(function (error) {
                                // handle error
                                var response = error.response;
                                _this.alert.message = response.data;
                            })
                            .then(function () {
                                // always executed
                            });
                }
            }
        }
    });
</script>
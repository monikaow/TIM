<#assign spring=JspTaglibs["http://www.springframework.org/tags"]>

<script type="text/x-template" id="vc-auth-login">
    <vc-section-header title="<@spring.message code="common.sign_in"/>" :max-width="'500px'" style="margin: auto;">

        <#if Request['LOGOUT_MESSAGE']?? >
            <v-flex xs12 class="text--secondary text-xs-center">
                <b class="subheading">${Request['LOGOUT_MESSAGE']}</b>
            </v-flex>
        </#if>

        <#if Request['AUTH_EXCEPTION']?? >
            <v-flex xs12 class="error--text text-xs-center">
                <b class="subheading">${Request['AUTH_EXCEPTION']}</b>
            </v-flex>
        </#if>

        <v-form v-model="form.valid" method="POST" ref="form" lazy-validation @submit="submit">
            <v-container grid-list-md>
                <v-layout wrap>
                    <v-flex xs12>
                        <v-text-field
                                browser-autocomplete
                                v-model="credentials.email"
                                name="username"
                                :rules="[$rules.required, $rules.email]"
                                label="<@spring.message code="auth.login.email"/>"
                                placeholder=" "
                        ></v-text-field>
                    </v-flex>
                    <v-flex xs12>
                        <v-text-field
                                browser-autocomplete
                                v-model="credentials.password"
                                :append-icon="showPassword ? 'visibility_off' : 'visibility'"
                                :type="showPassword ? 'text' : 'password'"
                                :rules="[$rules.required]"
                                name="password"
                                label="<@spring.message code="auth.login.password"/>"
                                placeholder=" "
                                @click:append="showPassword = !showPassword"
                        ></v-text-field>
                    </v-flex>
                    <v-flex xs12>

                        <v-card-actions>
                            <v-spacer></v-spacer>
                            <v-btn
                                    type="submit"
                                    color="primary">
                                <@spring.message code="common.sign_in"/>
                            </v-btn>
                        </v-card-actions>
                    </v-flex>
                </v-layout>
            </v-container>
        </v-form>
    </vc-section-header>
</script>
<script>
    Vue.component('vc-auth-login', {
        template: '#vc-auth-login',
        props: [],
        data: function () {
            return {
                form: {
                    valid: false
                },
                credentials: {
                    email: '',
                    password: '',
                },
                showPassword: false
            }
        },
        methods: {
            submit: function (e) {
                if (!this.$refs.form.validate()) {
                    e.preventDefault();
                }
            }
        }
    });
</script>
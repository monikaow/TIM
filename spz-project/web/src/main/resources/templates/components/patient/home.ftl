<#assign spring=JspTaglibs["http://www.springframework.org/tags"]>

<script type="text/x-template" id="vc-patient-home">

    <vc-empty-section-headline title="Strona startowa - panel pacjenta">

        <v-container
                class="pa-0"
                fluid
                grid-list-md
        >
            <v-layout row wrap>
                <v-flex xs12 >
                    <v-card href="/patient/reservation-visit">
                        <v-card-title primary-title>
                            <div>
                               <div class="headline"><span class="blue--text"><@spring.message code="views.patient.reservation-visit"/></span></div>
                               <div>Wybierz interesującą Cię specjalizację, lekarza oraz termin wizyty.</div>
                            </div>
                        </v-card-title>
                    </v-card>
                </v-flex>
                <v-flex xs12>
                    <v-card href="/patient/recipe-history">
                        <v-card-title primary-title>
                            <div>
                                <div class="headline"><span class="blue--text"><@spring.message code="views.patient.recipe-history"/></span></div>
                                <div>Zobacz historię przepisanych recept, sprawdź termin recepty do odbioru.</div>
                            </div>
                        </v-card-title>
                    </v-card>
                </v-flex>
                <v-flex xs12 >
                    <v-card href="/patient/manage-visits">
                        <v-card-title primary-title>
                            <div>
                                <div class="headline"><span class="blue--text"><@spring.message code="views.patient.manage-visits"/></span></div>
                                <div>Zobacz historię wizyt, wyświetl szczegóły wizyt, sprawdź swoje przyszłe wizyty lekarskie, anuluj wizytę.</div>
                            </div>
                        </v-card-title>
                    </v-card>
                </v-flex>
                <v-flex xs12>
                    <v-card href="/patient/details">
                        <v-card-title primary-title>
                            <div>
                                <div class="headline"><span class="blue--text"><@spring.message code="views.patient.details"/></span></div>
                                <div>Zmień swoje dane kontaktowe.</div>
                            </div>
                        </v-card-title>
                    </v-card>
                </v-flex>
            </v-layout>
        </v-container>

    </vc-empty-section-headline>
</script>
<script>
    Vue.component('vc-patient-home', {
        template: '#vc-patient-home',
        props: [],
        data: function () {
            return {}
        }
    });
</script>
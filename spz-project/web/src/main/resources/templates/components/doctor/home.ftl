<#assign spring=JspTaglibs["http://www.springframework.org/tags"]>

<script type="text/x-template" id="vc-doctor-home">

    <vc-empty-section-headline title="Strona startowa - panel lekarza">
        <v-container
                class="pa-0"
                fluid
                grid-list-md
        >
            <v-layout row wrap>
                <v-flex xs12 >
                    <v-card href="/doctor/manage-visits">
                        <v-card-title primary-title>
                            <div>
                                <div class="headline"><span class="blue--text">Zarządzaj wizytami</span></div>
                                <div>Widok kalendarza - Sprawdź swoje terminy wizyt, przepisz receptę, sprawdź historię recept pacjenta.</div>
                            </div>
                        </v-card-title>
                    </v-card>
                </v-flex>
                <v-flex xs12>
                    <v-card href="/doctor/details">
                        <v-card-title primary-title>
                            <div>
                                <div class="headline"><span class="blue--text">Edytuj swoje dane osobowe</span></div>
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
    Vue.component('vc-doctor-home', {
        template: '#vc-doctor-home',
        props: [],
        data: function () {
            return {}
        }
    });
</script>
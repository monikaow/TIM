<#assign spring=JspTaglibs["http://www.springframework.org/tags"]>

<script type="text/x-template" id="vc-public-home">
    <vc-section-headline title="<@spring.message code="public.home.title"/>">
        <div>
           <div class="red--text headline mb-3"><@spring.message code="public.home.welcome"/></div>

            <v-img src="/static/img/medicine.jpg"></v-img>
            <div><@spring.message code="public.home.action"/></div>

            <a href="/auth/login"><@spring.message code="common.sign_in"/></a>
            <a href="/auth/patient/register"><@spring.message code="common.sign_up"/></a>
        </div>
    </vc-section-headline>
</script>
<script>
    Vue.component('vc-public-home', {
        template: '#vc-public-home',
        props: [],
        data: function () {
            return {}
        }
    });
</script>


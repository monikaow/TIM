<#assign spring=JspTaglibs["http://www.springframework.org/tags"]>

<script type="text/x-template" id="vc-public-error">
    <vc-section-headline title="Wystąpił błąd">
        <h4>Błąd ${code}</h4>
            <a href="/">Powrót na stronę główną</a>
    </vc-section-headline>
</script>
<script>
    Vue.component('vc-public-error', {
        template: '#vc-public-error',
        props: [],
        data: function () {
            return {}
        }
    });
</script>


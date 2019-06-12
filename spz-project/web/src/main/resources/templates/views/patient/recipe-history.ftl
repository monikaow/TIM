<#assign spring=JspTaglibs["http://www.springframework.org/tags"]>
<#import "../../templates/patient-template.ftl" as m>

<#assign title><@spring.message code="views.patient.recipe-history"/></#assign>
<@m.template title=title
nestedComponent="/components/patient/recipe-history.ftl"
>
    <vc-patient-recipe-history>
    </vc-patient-recipe-history>
</@m.template>
<#assign spring=JspTaglibs["http://www.springframework.org/tags"]>
<#import "../../templates/patient-template.ftl" as m>

<#assign title><@spring.message code="views.patient.details"/></#assign>
<@m.template title=title
nestedComponent="/components/patient/details.ftl"
>
    <vc-patient-details>
    </vc-patient-details>
</@m.template>
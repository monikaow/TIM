<#assign spring=JspTaglibs["http://www.springframework.org/tags"]>
<#import "../../templates/patient-template.ftl" as m>

<#assign title><@spring.message code="views.patient.home"/></#assign>
<@m.template title=title
nestedComponent="/components/patient/home.ftl"
>
    <vc-patient-home>
    </vc-patient-home>
</@m.template>
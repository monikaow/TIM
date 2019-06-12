<#assign spring=JspTaglibs["http://www.springframework.org/tags"]>
<#import "../../templates/patient-template.ftl" as m>

<#assign title><@spring.message code="views.patient.register"/></#assign>
<@m.template title=title
nestedComponent="/components/patient/register.ftl"
showNavigationDriver=false
>
    <vc-patient-register>
    </vc-patient-register>
</@m.template>
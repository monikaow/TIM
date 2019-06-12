<#assign spring=JspTaglibs["http://www.springframework.org/tags"]>
<#import "../../templates/patient-template.ftl" as m>

<#assign title><@spring.message code="views.patient.manage-visits"/></#assign>
<@m.template title=title
nestedComponent="/components/patient/manage-visits.ftl"
>
    <vc-patient-manage-visits>
    </vc-patient-manage-visits>
</@m.template>
<#assign spring=JspTaglibs["http://www.springframework.org/tags"]>
<#import "../../templates/patient-template.ftl" as m>

<#assign title><@spring.message code="views.patient.reservation-visit"/></#assign>
<@m.template title=title
nestedComponent="/components/patient/reservation-visit.ftl"
>
    <vc-patient-reservation-visit>
    </vc-patient-reservation-visit>
</@m.template>
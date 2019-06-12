<#assign spring=JspTaglibs["http://www.springframework.org/tags"]>
<#import "../../templates/doctor-template.ftl" as m>

<#assign title><@spring.message code="views.doctor.details"/></#assign>
<@m.template title=title
nestedComponent="/components/doctor/details.ftl"
>
    <vc-doctor-details>
    </vc-doctor-details>
</@m.template>
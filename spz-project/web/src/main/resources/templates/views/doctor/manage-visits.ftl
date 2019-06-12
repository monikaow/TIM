<#assign spring=JspTaglibs["http://www.springframework.org/tags"]>
<#import "../../templates/doctor-template.ftl" as m>

<#assign title><@spring.message code="views.doctor.manage-visits"/></#assign>
<@m.template title=title
nestedComponent="/components/doctor/manage-visits.ftl"
>
    <vc-doctor-manage-visits>
    </vc-doctor-manage-visits>
</@m.template>
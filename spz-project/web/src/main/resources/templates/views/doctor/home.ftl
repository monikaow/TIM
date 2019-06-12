<#assign spring=JspTaglibs["http://www.springframework.org/tags"]>
<#import "../../templates/doctor-template.ftl" as m>

<#assign title><@spring.message code="views.doctor.home"/></#assign>
<@m.template title=title
nestedComponent="/components/doctor/home.ftl"
>
    <vc-doctor-home>
    </vc-doctor-home>
</@m.template>
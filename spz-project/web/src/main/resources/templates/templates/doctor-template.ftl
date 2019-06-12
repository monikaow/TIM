<#assign spring=JspTaglibs["http://www.springframework.org/tags"]>

<#import "../templates/common-template.ftl" as m>
<#macro template title nestedComponent="" >

    <#assign manageVisitsTitle><@spring.message code="views.doctor.manage-visits"/></#assign>
    <#assign detailsTitle><@spring.message code="views.doctor.details"/></#assign>

    <#assign menu=[
    {'icon': 'add', 'title': '${manageVisitsTitle}', 'to': '/doctor/manage-visits'},
    {'icon': 'edit', 'title': '${detailsTitle}', 'to': '/doctor/details'}
    ]>

    <@m.template title=title nestedComponent=nestedComponent showNavigationDriver=true menu=menu>
        <#nested>
    </@m.template>
</#macro>
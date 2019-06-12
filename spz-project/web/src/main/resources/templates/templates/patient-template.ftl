<#assign spring=JspTaglibs["http://www.springframework.org/tags"]>

<#import "../templates/common-template.ftl" as m>
<#macro template title nestedComponent="" showNavigationDriver=true >
    <#assign reservationVisitTitle><@spring.message code="views.patient.reservation-visit"/></#assign>
    <#assign recipeHistoryTitle><@spring.message code="views.patient.recipe-history"/></#assign>
    <#assign manageVisitsTitle><@spring.message code="views.patient.manage-visits"/></#assign>
    <#assign detailsTitle><@spring.message code="views.patient.details"/></#assign>

    <#assign menu=[
    {'icon': 'add', 'title': '${reservationVisitTitle}', 'to': '/patient/reservation-visit'},
    {'icon': 'list_alt', 'title': '${recipeHistoryTitle}', 'to': '/patient/recipe-history'},
    {'icon': 'info', 'title': '${manageVisitsTitle}', 'to': '/patient/manage-visits'},
    {'icon': 'edit', 'title': '${detailsTitle}', 'to': '/patient/details'}
    ]>

    <@m.template title=title nestedComponent=nestedComponent showNavigationDriver=showNavigationDriver menu=menu>
        <#nested>
    </@m.template>
</#macro>
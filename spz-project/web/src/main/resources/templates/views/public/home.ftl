<#assign spring=JspTaglibs["http://www.springframework.org/tags"]>
<#import "../../templates/common-template.ftl" as m>

<#assign title><@spring.message code="views.public.home"/></#assign>
<@m.template title=title
nestedComponent="/components/public/home.ftl"
showNavigationDriver=false
>
    <vc-public-home>
    </vc-public-home>
</@m.template>
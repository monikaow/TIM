<#assign spring=JspTaglibs["http://www.springframework.org/tags"]>
<#import "../../templates/common-template.ftl" as m>

<#assign title><@spring.message code="views.auth.login"/></#assign>
<@m.template title=title
nestedComponent="/components/auth/login.ftl"
showNavigationDriver=false
center=true
>
    <vc-auth-login>
    </vc-auth-login>
</@m.template>
<#include "parts/references.ftl">
<#include "parts/security.ftl">

<#import "parts/common.ftl" as c>
<#import "parts/authorization.ftl" as auth>

<@c.page "Login">
   <@auth.login />
</@c.page>
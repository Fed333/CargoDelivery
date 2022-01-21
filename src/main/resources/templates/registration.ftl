<#include "parts/references.ftl">
<#include "parts/security.ftl">

<#import "parts/common.ftl" as c>
<#import "parts/authorization.ftl" as auth>

<@c.page "Registration">
    <h1>Registration</h1>
    <form action="/registration" name="registerForm" id="registerForm" method="post">
        <button type="submit" class="btn btn-primary">Submit</button>
    </form>
</@c.page>
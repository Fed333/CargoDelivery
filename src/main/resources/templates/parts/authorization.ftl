<#include "references.ftl">
<#include "security.ftl">
<#import "/spring.ftl" as spring/>

<#macro login>
<div class="row d-flex justify-content-center mt-4">
    <form action="/login" method="post" style="max-width: 480px;">
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <div class="card">
            <div class="card-body">
                <div class="form-group row">
                    <div class="col d-flex justify-content-center mb-2">
                        <h2><@spring.message "auth.login.head"/></h2>
                    </div>
                </div>
                <div class="form-group row justify-content-center mb-2">
                    <input class="form-control" type="text" name="username" placeholder="<@spring.message "auth.credentials-login"/>" id="input_login">
                </div>
                <div class="form-group row justify-content-center mb-2">
                    <input class="form-control" type="password" name="password" placeholder="<@spring.message "auth.credentials-password"/>" id="input_password">
                </div>
                <div class="form-group row mx-5">
                    <label class="col-form label"><a href="${refForgotPassword}"><@spring.message "auth.login.forgot-password"/></a></label>
                </div>
                <div class="form-group row mx-5">
                    <label class="col-form label"><@spring.message "auth.login.have-not-registered"/> <a href="${refRegister}"><@spring.message "auth.login.register-now"/></a></label>
                </div>
                <div class="form-group row justify-content-center">
                    <button class="btn btn-primary mt-4" type="submit" id="button_sign_in"><@spring.message "auth.login"/></button>
                </div>
            </div>
        </div>
    </form>
</div>

</#macro>

<#macro logout>
<form class="d-flex mb-0" action="/logout" method="post">
    <input class="form-control" type="hidden" name="_csrf" value="${_csrf.token}"/>
    <button type="submit" class="btn btn-light"><@spring.message "auth.logout"/></button>
</form>
</#macro>
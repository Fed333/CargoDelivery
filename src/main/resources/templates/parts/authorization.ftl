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

<#macro registration>
<div class="row d-flex justify-content-center mt-4">
    <form action="/registration" method="post" style="max-width: 860px;">
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <div class="card">
            <div class="card-body">
                <div class="form-group row">
                    <div class="col d-flex justify-content-center">
                        <h2><@spring.message "auth.registration.head"/></h2>
                    </div>
                </div>
                <hr>
                <div class="form-group row">
                    <div class="d-flex justify-content-center mb-2">
                        <h3><@spring.message "auth.registration.personal-data.head"/></h3>
                    </div>
                </div>
                <div class="form-group row">
                    <div class="d-flex justify-content-center">
                        <div class="col-4 me-4">
                            <input class="form-control mt-2" type="text" name="name" id="nameInput" placeholder="<@spring.message "lang.name"/>">
                            <input class="form-control mt-2" type="text" name="surname" id="surnameInput" placeholder="<@spring.message "lang.surname"/>">
                        </div>
                        <div class="col-4">
                            <input class="form-control mt-2" type="email" name="email" id="emailInput" placeholder="<@spring.message "lang.email"/>">
                            <input class="form-control mt-2" type="text" name="email" id="phoneInput" placeholder="<@spring.message "lang.phone"/>">
                        </div>
                    </div>
                </div>
                <hr>
                <div class="form-group row">
                    <div  class="d-flex justify-content-center mb-2">
                        <h3><@spring.message "auth.registration.address-data.head"/></h3>
                    </div>
                </div>
                <div class="form-group row">
                    <div class="col-4">
                        <select class="form-select" name="address.cityId">
                            <#list cities as city>
                                <option value="${city.id}" id="city${city?index}">${city.name}</option>
                            </#list>
                        </select>
                    </div>
                    <div class="col-4">
                        <input class="form-control" type="text" name="address.streetName" id="streetInput" placeholder="<@spring.message "auth.registration.street-data"/>">
                    </div>
                    <div class="col-4">
                        <input class="form-control" type="text" name="address.houseNumber" id="houseNumberInput" placeholder="<@spring.message "auth.registration.house-number-data"/>">
                    </div>
                </div>
                <hr>
                <div class="form-group row">
                    <div class="d-flex justify-content-center mb-2">
                        <h3><@spring.message "auth.registration.credentials-data.head"/></h3>
                    </div>
                </div>
                <div class="form-group row">
                    <div class="col-4">
                        <input class="form-control" type="text" name="login" id="loginInput" placeholder="<@spring.message "auth.credentials-login"/>">
                    </div>
                    <div class="col-4">
                        <input class="form-control" type="password" name="password" id="passwordInput" placeholder="<@spring.message "auth.credentials-password"/>">
                    </div>
                    <div class="col-4">
                        <input class="form-control" type="password" name="duplicatePassword" id="duplicatePasswordInput" placeholder="<@spring.message "auth.credentials-duplicate-password"/>">
                    </div>
                </div>
                <hr>
                <div class="form-group row">
                    <div class="col d-flex justify-content-center">
                        <button class="btn btn-primary" id="button_register" type="submit"><@spring.message "auth.registration.button"/></button>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>
</#macro>
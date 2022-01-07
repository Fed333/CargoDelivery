<#include "references.ftl">
<#include "security.ftl">

<#macro login>
<div class="row d-flex justify-content-center mt-4">
    <form action="/login" method="post" style="max-width: 480px;">
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <div class="card">
            <div class="card-body">
                <div class="form-group row">
                    <div class="col d-flex justify-content-center mb-2">
                        <h2>Please Sign In</h2>
                    </div>
                </div>
                <div class="form-group row justify-content-center mb-2">
                    <input class="form-control" type="text" name="username" placeholder="Login" id="input_login">
                </div>
                <div class="form-group row justify-content-center mb-2">
                    <input class="form-control" type="password" name="password" placeholder="Password" id="input_password">
                </div>
                <div class="form-group row mx-5">
                    <label class="col-form label"><a href="${refForgotPassword}">Forgot password</a></label>
                </div>
                <div class="form-group row mx-5">
                    <label class="col-form label">Haven't registered yet? <a href="${refRegister}">Register now!</a></label>
                </div>
                <div class="form-group row justify-content-center">
                    <button class="btn btn-primary mt-4" type="submit" id="button_sign_in">Sign In</button>
                </div>
            </div>
        </div>
    </form>
</div>

</#macro>

<#macro logout>
<form class="d-flex mb-0" action="/logout" method="post">
    <input class="form-control" type="hidden" name="_csrf" value="${_csrf.token}"/>
    <button type="submit" class="btn btn-light">Log Out</button>
</form>
</#macro>
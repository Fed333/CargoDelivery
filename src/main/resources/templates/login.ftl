<#include "parts/references.ftl">
<#import "parts/common.ftl" as c>

<@c.page "Login">
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
                        <input class="form-control" type="text" name="login" placeholder="Login" id="input_login">
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
</@c.page>
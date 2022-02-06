<#import "parts/common.ftl" as c>
<#import "/spring.ftl" as spring/>

<#include "parts/references.ftl">

<@c.page "Make Receipt">

<#if customer.name?? && customer.surname??>
    <#assign
        firstNameLastName = customer.name + " " + customer.surname
    >
</#if>

<div class="row">
    <div class="col d-flex justify-content-center">
        <form action="/application/${application.id}/accept" method="post" style="min-width: 480px;">
            <input name="_csrf" value="${_csrf.token}" hidden>
            <div class="card">
                <div class="card-body">
                    <h1 class="d-flex justify-content-center">Delivery Receipt</h1>
                    <hr>
                    <div class="row mb-2">
                        <div class="col fs-5 fw-bolder">Application: </div>
                        <div class="col"> <a href="${refApplication}/${application.id}">#${application.id}</a> </div>
                    </div>
                    <div class="row mb-2">
                        <div class="col fs-5 fw-bolder">Customer: </div>
                        <div class="col"> <a href="/profile/${customer.id}"><#if firstNameLastName??>${firstNameLastName}<#else>${customer.login!"Unknown"}</#if></a> </div>
                    </div>
                    <div class="row mb-2">
                        <div class="col fs-5 fw-bolder">Manager: </div>
                        <div class="col"> <a href="/profile/${manager.id}">${manager.name!"Unknown"} ${manager.surname!""}</a></div>
                    </div>
                    <div class="row mb-2">
                        <div class="col fs-5 fw-bolder">Total price:</div>
                        <div class="col">
                            <div class="row">
                                <div class="col">
                                    <input type="text" class="form-control" name="price" value="${price!""}" placeholder="Price">
                                </div>
                                <div class="col">
                                    <label class="col-form-label">UAH</label>
                                </div>
                            </div>
                        </div>
                    </div>
                    <hr>
                    <div class="row">
                        <div class="col d-flex justify-content-center">
                            <button class="btn btn-success">Make</button>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>
</@c.page>
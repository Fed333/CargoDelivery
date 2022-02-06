<#import "parts/common.ftl" as c>
<#import "/spring.ftl" as spring/>

<#include "parts/references.ftl">

<@c.page "Confirmation Failed">

<div class="row">
    <div class="col d-flex justify-content-center">

        <div class="card" style="min-width: 480px;">
            <div class="card-body">
                <h2 class="d-flex justify-content-center">Confirmation Failed</h2>
                <hr>
                <#if application??>
                <div class="row mb-2">
                    <div class="col">
                        <label>The application </label>
                        <a href="${refApplication}/${application.id}">#${application.id}</a>
                        <label>could not be confirmed</label>
                    </div>
                </div>
                <div class="row">
                    <div class="col">
                        <label class="fw-bolder me-2">Application state:</label>
                        <label>${application.state}</label>
                    </div>
                </div>
                <hr>
                </#if>
                <div class="row">
                    <div class="col d-flex justify-content-center">
                        <a class="btn btn-outline-success" href="${refApplicationsReview}">OK</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</@c.page>
<#import "parts/common.ftl" as c>
<#import "/spring.ftl" as spring/>
<#import "parts/layouts.ftl" as l>

<#include "parts/references.ftl">

<@c.page "Application">

    <#if application??>
        <div class="row">
            <div class="col d-flex justify-content-center">
                <div class="card" style="min-width: 740px;">
                    <div class="card-body">
                        <@l.application application/>
                        <div class="row">
                            <div class="col d-flex justify-content-center">
                                <a class="btn btn-primary" href="${refProfile}">Back to Profile</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </#if>

</@c.page>
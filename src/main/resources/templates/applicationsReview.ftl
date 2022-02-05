<#import "parts/common.ftl" as c>
<#import "parts/pager.ftl" as p>
<#import "/spring.ftl" as spring/>

<#include "parts/references.ftl">

<@c.page "Applications Review">

<#if deliveryApplicationsReviewFilterRequest??>

    <#assign
        filterRequest = deliveryApplicationsReviewFilterRequest
        selectedSenderCityId = filterRequest.cityFromId!""
        selectedReceiverCityId = filterRequest.cityToId!""
        sendingDate = filterRequest.sendingDate!""
        receivingDate = filterRequest.receivingDate!""
    >
    <#if deliveryApplicationsReviewFilterRequest.applicationState??>
        <#assign
            selectedState = deliveryApplicationsReviewFilterRequest.applicationState
        >
    </#if>
    <#if deliveryApplicationsReviewFilterRequest.baggageType??>
        <#assign
            selectedType = deliveryApplicationsReviewFilterRequest.baggageType
        >
    </#if>
</#if>

<script src="/static/js/localization.js"></script>
<script src="/static/js/formSubmit.js"></script>

<div class="row">
    <div class="col d-flex justify-content-center">
        <h1>Delivery Applications Review</h1>
    </div>
</div>

<hr>

<form action="/applications/review" method="get">
    <input name="lang" value="${lang!"en"}" id="langInput" hidden>
    <div class="row">
        <div class="col" style="max-width: 380px;">
            <div class="row">
                <h2 class="d-flex justify-content-center">Filter</h2>
            </div>
            <div class="row">
                <div class="col filter-element" filter-name="Sender City">
                    <select class="form-select" name="cityFromId" id="senderCitySelect">
                        <option value="">ANY</option>
                        <#if cities??>
                            <#list cities?sort_by("name") as city>
                                <option value="${city.id}" id="senderCity${city.id}">${city.name}</option>
                                <script>selectOption('senderCity${city.id}', '${city.id}'==='${selectedSenderCityId!""}')</script>
                            </#list>
                        </#if>
                    </select>
                </div>
                <div class="col filter-element" filter-name="Receiver City">
                    <select class="form-select "  name="cityToId" id="receivingCitySelect">
                        <option value="">ANY</option>
                        <#if cities??>
                            <#list cities?sort_by("name") as city>
                                <option value="${city.id}" id="receiverCity${city.id}">${city.name}</option>
                                <script>selectOption('receiverCity${city.id}', '${city.id}'==='${selectedReceiverCityId!""}')</script>
                            </#list>
                        </#if>
                    </select>
                </div>
            </div>
            <div class="row">
                <div class="col filter-element" filter-name="Application State">
                    <select class="form-select" name="applicationState">
                        <option value="">ALL</option>
                        <#if states??>
                            <#list states as state>
                                <option value="${state}" <#if state == selectedState!"">selected</#if>>${state}</option>
                            </#list>
                        </#if>
                    </select>
                </div>
                <div class="col filter-element" filter-name="Baggage Type">
                    <select name="baggageType" id="baggageTypeSelect" class="form-select">
                        <option value="">ALL</option>
                        <#if types??>
                            <#list types as type>
                                <option value="${type}" <#if type == selectedType!"">selected</#if>>${type}</option>
                            </#list>
                        </#if>
                    </select>
                </div>
            </div>
            <div class="row">
                <div class="col-6 filter-element" filter-name="Sending Date">
                    <input class="form-control" type="date" name="sendingDate" value="${sendingDate!""}" id="sendingDateInput">
                </div>
                <div class="col-6 filter-element" filter-name="Receiving Date">
                    <input class="form-control" type="date" name="receivingDate" value="${receivingDate}" id="receivingDateInput">
                </div>
            </div>
            <div class="row mt-4">
                <div class="col d-flex justify-content-center">
                    <button class="btn btn-primary" type="submit" id="filterButton">Filter</button>
                </div>
            </div>
        </div>

        <div class="col ms-4">
            <div class="row alert alert-dark mb-2">
                <div class="col-1">
                    #id
                </div>
                <div class="col-5">
                    Direction
                </div>
                <div class="col-3">
                    Dates
                </div>
                <div class="col-1" style="min-width: 110px;">
                    Price
                </div>
                <div class="col-1">
                    State
                </div>
            </div>
            <#if applicationsPage?? && applicationsPage.content??>
                <#list applicationsPage.content as application>
                <div class="row alert alert-primary mb-2">
                    <div class="col-1">
                        <a class="link" href="${refApplication}/${application.id}">#${application.id}</a>
                    </div>
                    <div class="col-5">
                        "<@spring.message "city.${application.senderAddress.city.name}"/>" - "<@spring.message "city.${application.receiverAddress.city.name}"/>"
                    </div>
                    <div class="col-3">
                        ${application.sendingDate} - ${application.receivingDate}
                    </div>
                    <div class="col" style="max-width: 110px;">
                        ${application.price} <@spring.message "lang.UAH"/>
                    </div>
                    <div class="col">
                        <@spring.message "delivery-application.state.${application.state}"/>
                    </div>
                </div>
                </#list>
            </#if>

            <#if applicationsPage??>
                <@p.pager url applicationsPage 'filterButton'/>
            </#if>

        </div>
    </div>

</form>


<script>
    function clickSubmitButtonHandler(){
        clickSubmitButton('filterButton')
    }

    addEventListeners(clickSubmitButtonHandler)
</script>

</@c.page>
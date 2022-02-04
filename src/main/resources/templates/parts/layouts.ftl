<#import "/spring.ftl" as spring/>

<#macro application app>

<#if app.deliveredBaggage??>
    <#assign
        baggage = app.deliveredBaggage
        type = baggage.type!""
        volume = baggage.volume!""
        weight = baggage.weight!""
        description = baggage.description!""
    >
</#if>

<#if app.senderAddress??>
    <#assign
        senderAddress = app.senderAddress
        senderStreet = senderAddress.street!""
        senderHouseNumber = senderAddress.houseNumber!""
    >
    <#if app.senderAddress.city??>
        <#assign
            senderCityName = app.senderAddress.city.name!""
        >
    </#if>
</#if>

<#if app.receiverAddress??>
    <#assign
        receiverAddress = app.receiverAddress
        receiverStreet = receiverAddress.street!""
        receiverHouseNumber = receiverAddress.houseNumber!""
    >
    <#if app.receiverAddress.city??>
        <#assign
            receiverCityName = app.receiverAddress.city.name!""
        >
    </#if>
</#if>

<#if app.sendingDate??>
    <#assign
        sendingDate = app.sendingDate!""
    >
</#if>

<#if app.receivingDate??>
    <#assign
        receivingDate = app.receivingDate!""
    >
</#if>

    <div class="row">
        <h1 class="d-flex justify-content-center">Application #${app.id!""}</h1>
    </div>
    <hr>
    <div class="row">
        <h2 class="d-flex justify-content-center">Baggage</h2>
    </div>

    <div class="ms-4 me-4">

        <div class="row mb-2">
            <div class="col">
                <label class="fs-5 fw-bolder me-2">Type:</label> <label>${type!""}</label>
            </div>
        </div>

        <div class="row mb-2">
            <div class="col">
                <label class="fs-5 fw-bolder me-2">Volume:</label> <label>${volume!""} cm<sup><small>3</small></sup></label>
            </div>
            <div class="col">
                <label class="fs-5 fw-bolder me-2">Weight:</label> <label>${weight!""} kg</label>
            </div>
        </div>
        <div class="row">
            <div class="col">
                <label class="fs-5 fw-bolder me-2">Description:</label> <label>${description!""}</label>
            </div>
        </div>
    </div>

    <hr>
    <div class="row">
        <h2 class="d-flex justify-content-center">Address</h2>
    </div>

    <div class="ms-4 me-4">
        <div class="row mb-2">
            <div class="col">
                <label class="fs-5 fw-bolder me-2">Sender:</label> <label>${senderCityName!""}, street ${senderStreet!""}, house ${senderHouseNumber!""}</label>
            </div>
        </div>
        <div class="row">
            <div class="col">
                <label class="fs-5 fw-bolder me-2">Receiver:</label> <label>${receiverCityName!""}, street ${receiverStreet!""}, house ${receiverHouseNumber!""}</label>
            </div>
        </div>
    </div>

    <hr>
    <div class="ms-4 me-4">
        <div class="row mb-2">
            <div class="col-4">
                <label class="fs-5 fw-bolder me-2">Sending Date:</label>
            </div>
            <div class="col-2">
                <label>${sendingDate!""}</label>
            </div>
        </div>
        <div class="row">
            <div class="col-4">
                <label class="fs-5 fw-bolder me-2">Receiving Date:</label>
            </div>
            <div class="col-2">
                <label>${receivingDate!""}</label>
            </div>
        </div>
    </div>

    <hr>

    <div class="ms-4 me-4">
        <div class="row mb-2">
            <div class="col">
                <label class="fs-5 fw-bolder me-2">State:</label> <label>${app.state!""}</label>
            </div>
        </div>
        <div class="row">
            <div class="col">
                <label class="fs-5 fw-bolder me-2">Price:</label> <label>${app.price!""} UAH</label>
            </div>
        </div>
    </div>
    <hr>

</#macro>
<#import "parts/common.ftl" as c/>
<#import "parts/navtabs.ftl" as n/>
<#import "/spring.ftl" as spring/>
<#include "parts/references.ftl">

<@c.page "Delivery Cost Calculator">
    <#if distance??>
    <#assign
        selectedCitySenderId = distance.cityFrom.id
        selectedCityReceiverId = distance.cityTo.id
    >
    </#if>

    <#if calculatorRequest??>
    <#assign
        length = calculatorRequest.dimensions.length!""
        width = calculatorRequest.dimensions.width!""
        height = calculatorRequest.dimensions.height!""
        weight = calculatorRequest.weight!""
    >
    </#if>

    <#if .data_model["dimensions.lengthErrorMessage"]??>
    <#assign
        lengthErrorMessage = .data_model["dimensions.lengthErrorMessage"]
    >
    </#if>

    <#if .data_model["dimensions.widthErrorMessage"]??>
    <#assign
        widthErrorMessage = .data_model["dimensions.widthErrorMessage"]
    >
    </#if>

    <#if .data_model["dimensions.heightErrorMessage"]??>
    <#assign
        heightErrorMessage = .data_model["dimensions.heightErrorMessage"]
    >
    </#if>

    <#if .data_model["widthErrorMessage"]??>
    <#assign
        widthErrorMessage = .data_model["widthErrorMessage"]
    >
    </#if>

<script src="/static/js/formSubmit.js"></script>
    <script src="/static/js/localization.js"></script>
    <script src="/static/js/validationError.js"></script>

    <@n.infoPills "Calculator"/>

    <div class="row mb-2">
        <div class="col d-flex justify-content-center">
            <h2>Delivery Cost Calculator</h2>
        </div>
    </div>

    <form action="${refDeliveryCostCalculator}" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}">

        <div class="form-group row d-flex justify-content-center mt-4">
            <div class="col-2">
                <label class="col-form-label">Route</label>
            </div>

            <div class="col-4">
                <div class="form-group row">
                    <div class="col">
                        <select class="form-select" name="cityFromId" id="cityFromSelect">
                            <#if cities??>
                                <#list cities as citySender>
                                    <#assign citySenderId = citySender.id>
                                    <option value="${citySender.id}" id="citySender${citySenderId}">${citySender.name}</option>
                                    <script>selectOption('citySender${citySenderId}', '${citySenderId}'==='${selectedCitySenderId!""}')</script>
                                </#list>
                            </#if>
                        </select>
                    </div>

                    <div class="col">
                        <select class="form-select" name="cityToId" id="cityToSelect">
                            <#if cities??>
                                <#list cities as cityReceiver>
                                    <#assign cityReceiverId = cityReceiver.id>
                                    <option value="${cityReceiver.id}" id="cityReceiver${cityReceiverId}">${cityReceiver.name}</option>
                                    <script>selectOption('cityReceiver${cityReceiverId}', '${cityReceiverId}' === '${selectedCityReceiverId!""}')</script>
                                </#list>
                            </#if>
                        </select>
                    </div>
                </div>
            </div>

        </div>

        <div class="form-group row d-flex justify-content-center mt-4">
            <div class="col-2">
                <label class="col-form-label">Dimensions</label>
            </div>

            <div class="col-4">
                <div class="form-group row">
                    <div class="col validation-container" <#if lengthErrorMessage??>data-error="${lengthErrorMessage}!"</#if>>
                        <input class="form-control" type="text" name="dimensions.length" value="${length!""}" placeholder="length">
                    </div>

                    <div class="col validation-container" <#if widthErrorMessage??>data-error="${widthErrorMessage}!"</#if>>
                        <input class="form-control" type="text" name="dimensions.width" value="${width!""}" placeholder="width">
                    </div>

                    <div class="col validation-container" <#if heightErrorMessage??>data-error="${heightErrorMessage}!"</#if>>
                        <input class="form-control" type="text" name="dimensions.height" value="${height!""}" placeholder="height">
                    </div>

                    <div class="col">
                        <label class="col-form-label">cm</label>
                    </div>
                </div>
            </div>
        </div>

        <div class="form-group row d-flex justify-content-center mt-4">
            <div class="col-2">
                <label class="col-form-label">Weight</label>
            </div>

            <div class="col-4">
                <div class="form-group row">
                    <div class="col validation-container" <#if widthErrorMessage??>data-error="${widthErrorMessage}!"</#if>>
                        <input class="form-control" type="text" name="weight" value="${weight!""}" placeholder="weight">
                    </div>

                    <div class="col">
                        <label class="col-form-label">kg</label>
                    </div>
                </div>
            </div>
        </div>

        <div class="form-group row mt-4">
            <div class="d-flex justify-content-center">
                <button class="btn btn-primary" type="submit">Calculate</button>
            </div>
        </div>
    </form>

    <#if cost??>
    <div class="row" id="costDeliveryRow">
        <div class="alert alert-success col-auto" role="alert" id="costDeliveryContent">
            Delivery cost ${cost} UAH
        </div>
    </div>
    <a class="btn btn-primary" data-bs-toggle="collapse" href="#details" role="button" aria-expanded="false" aria-controls="details">
        Show me more
    </a>
    </#if>

    <div class="mt-4 collapse" id="details">

        <#if distance??>
        <div class="row" id="routeRow">
            <div class="alert alert-primary col-auto" role="alert" id="routeAlert">
                Route:
                <#list distance.route as city>
                ${city.name} <#sep> - </#sep>
                </#list>
            </div>
        </div>

        <div class="row" id="distanceRow">
            <div class="alert alert-primary col-auto" role="alert" id="distanceAlert">
                Distance ${distance.distance} km
            </div>
        </div>
        </#if>

    </div>

    <script>
        let inputs = []
        let url = '${refDeliveryCostCalculator}'
        addSwitchLanguageWithUrlClickListeners(url, inputs)
    </script>

    <script>addRemoveErrorAttributeListener()</script>

</@c.page>
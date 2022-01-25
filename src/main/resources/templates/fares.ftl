<#import "parts/common.ftl" as c/>
<#import "parts/navtabs.ftl" as n/>
<#include "parts/references.ftl">

<@c.page "Fares">
    <script src="/static/js/formSubmit.js"></script>
    <script src="/static/js/localization.js"></script>

    <@n.infoPills "Fares"/>

    <div class="row">
        <div class="col d-flex justify-content-center">
            <h1>Fares</h1>
        </div>
    </div>

    <div class="row mt-2">
        <div class="col ms-4 me-4">
            <div class="row mb-2">
                <h2 class="d-flex justify-content-center">Fares for distance</h2>
            </div>
            <div class="row">
                <table class="table table-bordered">
                    <thead class="table-primary">
                    <tr>
                        <th>Distance, km (not inclusive)</th>
                        <th>Price, UAH</th>
                    </tr>
                    </thead>
                    <tbody class="table-light">
                    <#list distanceFares?sort_by("price") as distanceFare>
                    <tr>
                        <#if distanceFare?index+1 == distanceFares?size>
                            <td>more than ${distanceFare.distanceTo}</td>
                        <#else>
                            <td>up to ${distanceFare.distanceTo+1}</td>
                        </#if>
                        <td>${distanceFare.price}</td>
                    </tr>
                    </#list>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="col ms-4 me-4">
            <div class="row mb-2">
                <h2 class="d-flex justify-content-center">Fares for weight</h2>
            </div>
            <div class="row">
                <table class="table table-bordered">
                    <thead class="table-primary">
                    <tr>
                        <th>Weight, kg (not inclusive)</th>
                        <th>Price, UAH</th>
                    </tr>
                    </thead>
                    <tbody class="table-light">
                    <#list weightFares as weightFare>
                    <tr>
                        <#if weightFare?index+1 == weightFares?size>
                            <td>for every ${weightFare.weightTo} over ${weightFare.weightTo}</td>
                        <#else>
                            <td>up to ${weightFare.weightTo+1}</td>
                        </#if>
                        <td>${weightFare.price}</td>
                    </tr>
                    </#list>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="col ms-4 me-4">
            <div class="row mb-2">
                <h2 class="d-flex justify-content-center">Fares for dimensions</h2>
            </div>
            <div class="row">
                <table class="table table-bordered">
                    <thead class="table-primary">
                    <tr>
                        <th>Volume, cm<sup><small>3</small></sup> (not inclusive)</th>
                        <th>Price, UAH</th>
                    </tr>
                    </thead>
                    <tbody class="table-light">
                    <#list dimensionsFares as dimensionsFare>
                    <tr>
                        <#if dimensionsFare?index+1 == dimensionsFares?size>
                            <td>for every ${dimensionsFare.dimensionsTo} over ${dimensionsFare.dimensionsTo}</td>
                        <#else>
                            <td>up to ${dimensionsFare.dimensionsTo+1}</td>
                        </#if>
                        <td>${dimensionsFare.price}</td>
                    </tr>
                    </#list>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <script>
        let inputs = []
        let url = '${refFares}'
        addSwitchLanguageWithUrlClickListeners(url, inputs)
    </script>
</@c.page>
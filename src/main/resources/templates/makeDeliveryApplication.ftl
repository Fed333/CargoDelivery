<#import "parts/common.ftl" as c/>
<#import "/spring.ftl" as spring/>
<#import "parts/forms.ftl" as f/>

<#include "parts/references.ftl">


<@c.page "Make Application">

    <script src="/static/js/localization.js"></script>

    <div class="row">
        <div class="col d-flex justify-content-center">
            <h1>Make Delivery Application</h1>
        </div>
    </div>

    <div class="row d-flex justify-content-center mt-4" >
        <form action="${refCreateApp}" method="post">
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <div class="card">
                <div class="card-body">
                    <div class="form-group row">
                        <div class="col d-flex justify-content-center mb-2">
                            <h2>Baggage</h2>
                        </div>
                    </div>
                    <div class="form-group row justify-content-center mt-4 mb-2">
                        <div class="col me-4">
                            <div class="row">
                                <div class="col">
                                    <label class="col-form-label">Volume</label>
                                </div>
                                <div class="col">
                                    <input class="form-control" name="deliveredBaggageRequest.volume" type="text">
                                </div>
                            </div>
                            <div class="row mt-2">
                                <div class="col">
                                    <label class="col-form-label">Weight</label>
                                </div>
                                <div class="col">
                                    <input class="form-control" name="deliveredBaggageRequest.weight" type="text">
                                </div>
                            </div>

                        </div>

                        <div class="col">
                            <div class="row">
                                <div class="col">
                                    <label class="col-form-label">Type</label>
                                </div>
                                <div class="col">
                                    <select class="form-select" name="deliveredBaggageRequest.type" >
                                    <#if types??>
                                        <#list types as type>
                                            <option value="${type}" id="type${type?index}">${type}</option>
                                        </#list>
                                    </#if>
                                    </select>
                                </div>
                            </div>
                            <div class="row mt-2">
                                <div class="col">
                                    <label class="col-form-label">Description</label>
                                </div>
                                <div class="col">
                                    <input class="form-control" name="deliveredBaggageRequest.description" type="text" >
                                </div>
                            </div>
                        </div>
                    </div>
                    <hr>
                    <div class="form-group row">
                        <div class="d-flex justify-content-center mb-2">
                            <h2>Address</h2>
                        </div>
                    </div>

                    <div class="form-group row justify-content-center mt-2 mb-2">
                        <div class="col">
                            <div class="row mb-4">
                                <h3 class="d-flex justify-content-center">Sending</h3>
                            </div>
                            <@f.address 'senderAddress.cityId', 'senderAddress.streetName', 'senderAddress.houseNumber'/>
                        </div>

                        <div class="col">
                            <div class="row mb-4">
                                <h3 class="d-flex justify-content-center">Receiving</h3>
                            </div>
                            <@f.address 'receiverAddress.cityId', 'receiverAddress.streetName', 'receiverAddress.houseNumber'/>
                        </div>
                    </div>
                    <hr>
                    <div class="form-group row">
                        <div class="d-flex justify-content-center mb-2">
                            <h2>Date</h2>
                        </div>
                    </div>
                    <div class="form-group row justify-content-center mt-2 mb-2">
                        <div class="col-4 me-4">
                            <div class="row mb-2">
                                <h3 class="d-flex justify-content-center mb-4">Sending</h3>
                                <input class="form-control" type="date" name="sendingDate">
                            </div>
                        </div>

                        <div class="col-4 ms-4">
                            <div class="row mb-2">
                                <h3 class="d-flex justify-content-center mb-4">Receiving</h3>
                                <input class="form-control" type="date" name="receivingDate">
                            </div>
                        </div>
                    </div>
                    <hr>
                    <div class="form-group row">
                        <div class="d-flex justify-content-center">
                            <button class="btn btn-primary" type="submit">Apply</button>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>

    <script>
        let inputs = []
        let url = '${refCreateApp}'
        addSwitchLanguageWithUrlClickListeners(url, inputs)
    </script>
</@c.page>

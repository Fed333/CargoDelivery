<#import "parts/common.ftl" as c>

<@c.page "Information Page">
    <div class="row">
        <div class="col d-flex justify-content-center">
            <h1>Directions Info</h1>
        </div>
    </div>

    <div class="row mt-3" style="margin-left: 10rem;">
        <div class="col-5">
            <table class="table table-bordered">
                <thead class="table-primary">
                <tr>
                    <th>Direction</th>
                    <th>Distance km</th>
                </tr>
                </thead>
                <tbody class="table-light">
                <#list directions as direction>
                <tr>
                    <td>${direction.senderCity.name} - ${direction.receiverCity.name}</td>
                    <td>${direction.distance}</td>
                </tr>
                </#list>
                </tbody>
            </table>
        </div>
    </div>

</@c.page>

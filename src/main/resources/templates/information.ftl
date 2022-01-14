<#import "parts/common.ftl" as c/>
<#import "/spring.ftl" as spring/>
<#import "parts/pager.ftl" as p/>
<#import "parts/forms.ftl" as f/>

<@c.page "Information Page">
    <div class="row">
        <div class="col d-flex justify-content-center">
            <h1><@spring.message "info.directions-head"/></h1>
        </div>
    </div>

    <div class="row mt-3">
        <div class="col-2">
            <!--<label>here could be your ads</label>-->
        </div>
        <div class="col-5">
            <form method="get" action="${url}" id="filterForm">
                <#assign
                options = [
                ["senderCity.name", "senderCityOption", "Sender City"],
                ["receiverCity.name", "receiverCityOption", "Receiver City"],
                ["distance", "distanceOption", "Distance"]
                ]
                >
                <div class="mb-3"><@f.sorting "filterForm" options order /></div>

                <table class="table table-bordered">
                    <thead class="table-primary">
                    <tr>
                        <th><@spring.message "lang.directions"/></th>
                        <th><@spring.message "lang.distance"/></th>
                    </tr>
                    </thead>
                    <tbody class="table-light">
                    <#list directionsPage.content as direction>
                    <tr>
                        <td>${direction.senderCity.name} - ${direction.receiverCity.name}</td>
                        <td>${direction.distance}</td>
                    </tr>
                    </#list>
                    </tbody>
                </table>
                <@p.pager url directionsPage 'filterForm'/>

            </form>
        </div>
    </div>

</@c.page>

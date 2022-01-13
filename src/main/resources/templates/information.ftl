<#import "parts/common.ftl" as c/>
<#import "/spring.ftl" as spring/>
<#import "parts/pager.ftl" as p/>

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
                    <td><@spring.message "city.${direction.senderCity.name}"/> - <@spring.message "city.${direction.receiverCity.name}"/></td>
                    <td>${direction.distance}</td>
                </tr>
                </#list>
                </tbody>
            </table>
            <@p.pager url directionsPage/>
        </div>
    </div>

</@c.page>

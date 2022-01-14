
<#macro sorting formId order>
<script src="/static/js/formSubmit.js"></script>
<div class="form-group row">
    <select class="form-select" name="sort" id="sortForm" onchange="formSubmit('${formId}')">
        <option value="senderCity.name" id="senderCityOption">Sender City</option>
        <option value="receiverCity.name" id="receiverCityOption">Receiver City</option>
        <option value="distance" id="distanceOption">Distance</option>
    </select>
        <#assign
        orderProperty = order.getProperty()
        descendingDirection = order.getDirection().isDescending()
        >

        <#list ["senderCityOption", "receiverCityOption", "distanceOption"] as id>
            <script>setSortOptionSelected('${id}', '${orderProperty}')</script>
        </#list>

</div>
</#macro>
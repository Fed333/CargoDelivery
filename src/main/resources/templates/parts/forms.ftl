
<#macro sorting formId options order>
<#assign
orderProperty = order.getProperty()
orderDirection = order.getDirection().toString()

>
<script src="/static/js/formSubmit.js"></script>
<div class="form-group row">
    <div class="col-4">
        <select class="form-select" name="sort" id="sortCriterionSelect">
            <#list options as optionArr>
                <option value="${optionArr[0]}" id="${optionArr[1]}">${optionArr[2]}</option>
                <script>setSortOptionSelected('${optionArr[1]}', '${orderProperty}')</script>
            </#list>
        </select>
    </div>
    <div class="col-3">
        <select class="form-select" id="sortOrderSelect">
            <option value="asc" id="ascOption">ASC</option>
            <option value="desc" id="descOption">DESC</option>
            <#list ['ascOption', 'descOption'] as id>
                <script>setSortOptionSelected('${id}', '${orderDirection}')</script>
            </#list>
        </select>
    </div>
</div>
<script>

    function setSortOptionOrderHandler(){
        setSortOptionOrder('sortOrderSelect', 'sortCriterionSelect')
    }

    function submitFormHandler(){
        formSubmit('${formId}')
    }

    sortCriterionSelect.addEventListener("change", setSortOptionOrderHandler, false)
    sortCriterionSelect.addEventListener("change", submitFormHandler, false)

    sortOrderSelect.addEventListener("change", setSortOptionOrderHandler, false)
    sortOrderSelect.addEventListener("change", submitFormHandler, false)

</script>
</#macro>
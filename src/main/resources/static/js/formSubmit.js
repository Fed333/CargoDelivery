
function formSubmit(formId){
//    console.log("formId: " + formId)
    document.getElementById(formId).submit()
}

function clickSubmitButton(submitButtonId){
    document.getElementById(submitButtonId).click()
}

function setSortOptionSelected(optionId, expectedValue){
    console.log('setSortOptionSelected has been called')
    option = document.getElementById(optionId)
    console.log("option.value: " + option.value + ", expectedValue: " + expectedValue)

    if (option.value.toLowerCase() === expectedValue.toLowerCase()){
        option.setAttribute('selected', 'selected')
        console.log("added selected attribute to " + optionId)
    }
}

function setSortOptionOrder(sortSelectOrderId, sortSelectCriterionId){
//    console.log('setSortOptionOrder has been called')

    selectCriterion = document.getElementById(sortSelectCriterionId)
    optionCriterion = selectCriterion.options[selectCriterion.selectedIndex]

    selectOrder = document.getElementById(sortSelectOrderId)

    optionOrder = selectOrder.options[selectOrder.selectedIndex]
    value = optionCriterion.getAttribute('value')
    value = value.replace(/(,[a-zA-Z]*)/, '')
    value = value + "," + optionOrder.getAttribute('value')

//    console.log("value: " + value)

    optionCriterion.setAttribute('value', value)
}

function setPageNumber(pageNumber){
    document.getElementById('pageNumber').setAttribute('value', pageNumber)
}

function setPageSize(pageSize){
    document.getElementById('pageSize').setAttribute('value', pageSize)
}
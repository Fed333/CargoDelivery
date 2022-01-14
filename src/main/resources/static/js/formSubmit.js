
function formSubmit(formId){
    console.log("formId: " + formId)
    document.getElementById(formId).submit()
}

function setSortOptionSelected(optionId, expectedValue){
    option = document.getElementById(optionId)
    console.log("option.value: " + option.value + ", expectedValue: " + expectedValue)

    if (option.value === expectedValue){
        option.setAttribute('selected', 'selected')
        console.log("added selected attribute to " + optionId)
    }
}
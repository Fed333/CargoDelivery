
function switchLanguageToUkrainianLocale(){
    switchLanguageToLocale('uk')
}

function switchLanguageToEnglishLocale(){
    switchLanguageToLocale('en')
}

function switchLanguageToLocale(lang){
     langInput.setAttribute('value', lang)
}

function addEventListeners(submitHandler){
    langEnglish.addEventListener('click', switchLanguageToEnglishLocale)
    langUkrainian.addEventListener('click', switchLanguageToUkrainianLocale)

    langEnglish.addEventListener('click', submitHandler)
    langUkrainian.addEventListener('click', submitHandler)
}
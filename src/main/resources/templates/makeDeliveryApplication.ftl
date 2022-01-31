<#import "parts/common.ftl" as c/>
<#include "parts/references.ftl">
<#import "/spring.ftl" as spring/>

<@c.page "Make Application">

    <script src="/static/js/localization.js"></script>

    <h1>Make Delivery Application</h1>
    <#if result??>
        <p>${result}</p>
    </#if>
    <script>
        let inputs = []
        let url = '${refCreateApp}'
        addSwitchLanguageWithUrlClickListeners(url, inputs)
    </script>
</@c.page>

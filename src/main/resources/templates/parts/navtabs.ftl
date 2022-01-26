<#include "references.ftl">
<#include "security.ftl">
<#import "/spring.ftl" as spring/>

<#macro infoPills activePill>
<#assign
    activePill = activePill!""
    directions = "Directions"
    fares = "Fares"
>
    <ul class="nav nav-pills">
        <li class="nav-item">
            <a class="nav-link <#if activePill == directions>active</#if>" href="${refInfo}" id="directionsPill"><@spring.message "lang.directions"/></a>
        </li>
        <li>
            <a class="nav-link <#if activePill == fares>active</#if>" href="${refFares}" id="faresPill"><@spring.message "lang.fares"/></a>
        </li>

    </ul>
</#macro>
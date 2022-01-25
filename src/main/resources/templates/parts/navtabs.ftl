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
            <a class="nav-link <#if activePill == directions>active</#if>" href="${refInfo}" id="directionsPill">Directions</a>
        </li>
        <li>
            <a class="nav-link <#if activePill == fares>active</#if>" href="${refFares}" id="faresPill">Fares</a>
        </li>

    </ul>
</#macro>
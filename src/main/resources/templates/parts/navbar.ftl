<#include "references.ftl">
<#include "security.ftl">

<#import "authorization.ftl" as auth>
<#import "/spring.ftl" as spring/>

<nav class="navbar navbar-expand-lg navbar-dark bg-green-ireland">
    <div class="container-fluid">

        <a class="navbar-brand" href="${refMain}"><@spring.message "menu.cargo-delivery"/></a>

        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarContent" aria-controls="navbarContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link" href="${refInfo}"><@spring.message "menu.directions"/></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${refCreateApp}"><@spring.message "menu.create-application"/></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${refProfile}"><@spring.message "menu.profile"/></a>
                </li>
            </ul>
            <div class="dropdown navbar-nav me-2" id="switchLanguageDropdown">
                <a class="nav-link active dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false" id="selectLanguageDropdown">
                   <img src="/static/images/i18n/language_icon.png" width="24" height="24" class="d-inline-block align-text-top">
                    <@spring.message "menu.language"/>
                </a>
                <ul class="dropdown-menu me-auto" aria-labelledby="selectLanguageDropdown">
                    <li>
                        <a class="dropdown-item" href="?lang=en">
                            <img src="/static/images/i18n/flags/english_flag.png" width="48" height="24" class="d-inline-block me-4">
                            <@spring.message "menu.language_en"/>
                        </a>
                    </li>
                    <li>
                        <a class="dropdown-item" href="?lang=uk">
                            <img src="/static/images/i18n/flags/ukraine_flag.png" width="48" height="24" class="d-inline-block me-4">
                            <@spring.message "menu.language_uk"/>
                        </a>
                    </li>
                </ul>
            </div>
            <#if authorized>
                <@auth.logout />
            <#else>
                <a class="btn btn-light me-2" href="${refLogin}"><@spring.message "auth.login"/></a>
            </#if>
        </div>
    </div>
</nav>
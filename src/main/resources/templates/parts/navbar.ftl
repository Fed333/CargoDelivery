<#include "references.ftl">
<nav class="navbar navbar-expand-lg navbar-dark bg-green-ireland">
    <div class="container-fluid">
        <a class="navbar-brand" href="${refMain}">CargoDelivery</a>

        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarContent" aria-controls="navbarContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link" href="${refInfo}">Directions</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${refCreateApp}">Create Application</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${refProfile}">Profile</a>
                </li>
            </ul>

            <a class="btn btn-light me-2" href="${refLogin}">Sign In</a>
        </div>
    </div>
</nav>
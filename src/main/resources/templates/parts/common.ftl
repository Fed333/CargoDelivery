<#import "/spring.ftl" as spring/>
<#macro page title>

<!DOCTYPE>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>${title}</title>

    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>

    <!-- Bootstrap CSS -->
    <!--    підключаємо стилі Bootstrap-->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

    <!-- Optional JavaScript -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js" integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>

    <link rel="stylesheet" href="/static/css/style.css">
    <link rel="stylesheet" href="/static/css/style_auth.css">
    <link rel="stylesheet" href="/static/css/style_navbar.css">
    <link rel="stylesheet" href="/static/css/style_input_error.css">

</head>
<body>

<div class="content">
    <#include "navbar.ftl"/>
    <div class="container mt-4">
        <#nested>
    </div>
</div>

</body>
</html>

</#macro>
<#import "/spring.ftl" as spring/>

<#macro pager url page formId>
    <#if page.getTotalPages() gt 4>
        <#assign
            totalPages = page.getTotalPages()
            pageNumber = page.getNumber() + 1

            head = (pageNumber > 4)?then([1, -1],[1, 2, 3])
            tail = (pageNumber > totalPages - 4)?then([totalPages - 2, totalPages - 1, totalPages], [-1, totalPages])
            bodyBefore = (pageNumber > 4 && pageNumber < totalPages - 1)?then([pageNumber - 2, pageNumber - 1], [])
            bodyAfter = (pageNumber < totalPages - 3 && pageNumber > 2)?then([pageNumber + 1, pageNumber + 2], [])

            body = head + bodyBefore + (pageNumber < 4 || pageNumber > totalPages - 3 )?then([], [pageNumber]) + bodyAfter + tail

        >
    <#else>
        <#assign body = 1..page.getTotalPages()>
    </#if>

    <script src="/static/js/formSubmit.js"></script>
    <div class="mt-2">
        <ul class="pagination">
            <li class="page-item disabled">
                <a class="page-link" href="#"><@spring.message "pagination.pages"/></a>
            </li>
            <#assign
                pageNumber = page.getNumber()
            >
            <#list body as p>
            <#if (p - 1) == page.getNumber()>
            <li class="page-item active">
                <a class="page-link" href="#" tabindex="-1">${p}</a>
            </li>
            <#elseif p == -1>
            <li class="page-item disabled">
                <a class="page-link" href="#" tabindex="-1">...</a>
            </li>
            <#else>
            <li class="page-item">
                <a class="page-link" href="#" role="button" tabindex="-1" id="page${p}">${p}</a>
                <script>
                    page = document.getElementById('page${p}')
                    page.addEventListener('click', ()=>{setPageNumber('${p-1}')})
                    page.addEventListener('click', ()=>{setPageSize('${page.getSize()}')})
                    page.addEventListener('click', ()=>{formSubmit('${formId}')})
                </script>
            </li>
            </#if>
            </#list>
        </ul>
        <input name="page" id="pageNumber" value="${pageNumber}" hidden>
        <input name="size" id="pageSize" value="${page.getSize()}" hidden>
    </div>
</#macro>
<#import "parts/common.ftl" as c>
<#import "/spring.ftl" as spring/>

<#setting number_format="computer">

<@c.page "Profit">
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>

    <script>

        function drawChart() {

            var data = google.visualization.arrayToDataTable([
                ['Month', 'Profit'],
                <#list profit as p>
                    ['${p.month}', ${p.profit}]<#sep>,</#sep>
                </#list>
            ]);

            var options = {
                chart: {
                    title: 'Profit of CargoDelivery monthly operations',
                    subtitle: 'in UAH'
                },

                width: 1000,
                height: 500
            };

            var chart = new google.charts.Line(document.getElementById('profit_linechart'));

            chart.draw(data, google.charts.Line.convertOptions(options));
        }

        google.charts.load('current', {'packages':['line']});
        google.charts.setOnLoadCallback(drawChart);


    </script>

    <div class="row mb-4">
        <h1 class="col d-flex justify-content-center">Profit</h1>
    </div>

    <div class="row">
        <div class="col d-flex justify-content-center">
            <div id="profit_linechart"></div>
        </div>
    </div>

</@c.page>
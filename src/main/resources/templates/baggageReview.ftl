<#include "parts/references.ftl">
<#include "parts/security.ftl">

<#import "parts/common.ftl" as c>
<#import "parts/authorization.ftl" as auth>

<@c.page "Baggage Review">
    <script src="/static/js/formSubmit.js"></script>
    <script src="/static/js/localization.js"></script>

    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">

        // Load the Visualization API and the corechart package.
        google.charts.load('current', {'packages':['corechart']});

        // Set a callback to run when the Google Visualization API is loaded.
        google.charts.setOnLoadCallback(drawChart);

        // Callback that creates and populates a data table,
        // instantiates the pie chart, passes in the data and
        // draws it.
        function drawChart() {

            // Create the data table.
            var data = new google.visualization.DataTable();
            data.addColumn('string', 'Topping');
            data.addColumn('number', 'Slices');
            data.addRows([
                <#list baggagePerTypeReport as baggagePerType>
                    ['${baggagePerType.type}', ${baggagePerType.amount}]<#sep>,</#sep>
                </#list>
            ]);
            // Set chart options
            var options = {'title':'Amount of shipped baggage per type',
                'width':400,
                'height':300};

            // Instantiate and draw our chart, passing in some options.
            var chart = new google.visualization.PieChart(document.getElementById('chart_div'));
            chart.draw(data, options);
        }
    </script>

    <div class="row">
        <h1 class="d-flex justify-content-center">Baggage Category Review</h1>
    </div>

    <div class="row">
        <div id="chart_div"></div>
    </div>

    <script>
        addSwitchLanguageWithUrlClickListeners('/baggage/review', [])
    </script>

</@c.page>
<html>
<head>

    <#macro css>

        <#nested>

    </#macro>

</head>

<body>

<div id="wrapper">
    <div>默认的模板</div>

    <#macro layout>

        <#nested>

    </#macro>
    </hr>
</div>


</body>

<#macro js>

    <#nested>

</#macro>

</html>

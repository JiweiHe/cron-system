<!DOCTYPE html>

<html lang="en">
<#include "../layout/header.ftl">
<body>
<div>
    <div class="triggers container" style="width:70%;margin-top:5%;">
        <a href="${base}/trigger/list">触发器列表</a>
        <table class="table table-striped table-bordered">
            <tr>
                <td>未被执行的触发器名称</td>
                <td>时间</td>
                <td>job</td>
            </tr>
            <#list logs as log>
                <tr>
                    <td>${log.triggerKey}</td>
                    <td>${(log.time)?number_to_datetime}</td>
                    <td>${log.jobKey}</td>
                </tr>
            </#list>
        </table>
    </div>
</div>
</body>
</html>
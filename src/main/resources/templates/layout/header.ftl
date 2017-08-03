<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
<script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
<script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<#setting number_format="0">
<#assign maps={"ACQUIRED":"正常执行","WAITING":"等待中", "PAUSED": "暂停", "BLOCKED" : "锁定中", "PAUSED_BLOCKED" : "暂停-锁定"}>
<#assign whether={"1":"是", "0" : "否"}>
<#assign base="/cron">
<#setting url_escaping_charset='utf-8'>
<style>
        body {
            margin: 0px;
            padding: 0px;
        }
</style>
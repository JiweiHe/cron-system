<!DOCTYPE html>

<html lang="en">
<#include "../layout/header.ftl">
<body>
<div>
    <div class="triggers container" style="width:70%;margin-top:5%;">
        <a href="${base}/trigger/list">trigger列表</a>
        <table class="table table-striped table-bordered" style="align-content:center">
            <tr>
                <td>job名称</td>
                <td>job组</td>
                <td>trigger</td>
                <td>triggerGroup</td>
                <td>job_class_name</td>
                <td>是否持久化</td>
                <td>是否更新数据</td>
                <td>非并发执行</td>
                <td>是否可恢复</td>
                <td>操作</td>
            </tr>
            <#list jobDetails as jobDetail>
                <tr>
                    <td>${jobDetail.JOB_NAME}</td>
                    <td>${jobDetail.JOB_GROUP}</td>
                    <td>${jobDetail.TRIGGER_NAME!}</td>
                     <td>${jobDetail.TRIGGER_GROUP!}</td>
                    <td>${jobDetail.JOB_CLASS_NAME}</td>
                    <td>${whether[jobDetail.IS_DURABLE]}</td>
                    <td>
                    ${whether[jobDetail.IS_UPDATE_DATA]}
                    </td>
                    <td>${whether[jobDetail.IS_NONCONCURRENT]}</td>
                    <td>
                    ${whether[jobDetail.REQUESTS_RECOVERY]}
                    </td>
                    <td>
                    <#if !jobDetail.TRIGGER_NAME?? >
                        <button class="btn btn-primary J-save-modal" data-toggle="modal" data-target="#triggerModal" data-job="${jobDetail.JOB_NAME}" data-jobgroup="${jobDetail.JOB_GROUP}">新建触发器</button>
                    <#else>
                        <a href="${base}/trigger/list?jobName=${jobDetail.JOB_NAME?url}&jobGroup=${jobDetail.JOB_GROUP?url}">触发器</a>
                    </#if>
                    </td>
            </tr>
            </#list>
        </table>
    </div>

<div class="modal fade" id="triggerModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">新建触发器</h4>
      </div>
      <div class="modal-body">
        触发器组:
        <input type="text" class="J-group form-control" value="apiTriggerGroup"/>
        触发器名称:
        <input type="text" class="J-trigger form-control" value=""/>
        定时cron:
        <input type="text" class="J-cron form-control"/>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary J-save" data-url="${base}/trigger/edit">Save changes</button>
      </div>
    </div>
  </div>
</div>
</body>
<script type="text/javascript" src="../js/jobdetail.js"></script>
</html>
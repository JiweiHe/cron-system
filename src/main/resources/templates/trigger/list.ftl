<!DOCTYPE html>

<html lang="en">
<#include "../layout/header.ftl">
<body>
<div>
    <div class="triggers container" style="width:70%;margin-top:5%;">
        <a href="${base}/jobDetail/list">job列表</a>
        <a href="${base}/trigger/logs">misFired列表</a>
        <table class="table table-striped table-bordered">
            <tr>
                <td>触发器名称</td>
                <td>cron表达式</td>
                <td>下次触发时间</td>
                <td>上次触发时间</td>
                <td>开始时间</td>
                <td>结束时间</td>
                <td>状态</td>
                <td>操作</td>
            </tr>
            <#list triggers as trigger>
                <tr>
                    <td>${trigger.TRIGGER_NAME}</td>
                    <td>${trigger.CRON_EXPRESSION}</td>
                    <td>${(trigger.NEXT_FIRE_TIME)?number_to_datetime}</td>
                    <td>${(trigger.PREV_FIRE_TIME)?number_to_datetime}</td>
                    <td>${(trigger.START_TIME)?number_to_datetime}</td>
                    <td>
                        <#if trigger.END_TIME <= 1>
                            ${(trigger.END_TIME)}
                        <#else>
                            ${(trigger.END_TIME)?number_to_datetime}
                        </#if>
                    </td>
                    <td>${maps[trigger.TRIGGER_STATE]}</td>
                    <td>
                    <#if trigger.TRIGGER_STATE == 'PAUSED'>
                        <a href="" class="J-resume" data-name="${trigger.TRIGGER_NAME}" data-group="${trigger.TRIGGER_GROUP}">
                            恢复
                        </a>
                     <#else>
                     <a href="" class="J-pause" data-name="${trigger.TRIGGER_NAME}" data-group="${trigger.TRIGGER_GROUP}">
                        暂停
                     </a>
                     </#if>
                     <br>
                    <a href="" class="J-remove" data-name="${trigger.TRIGGER_NAME}" data-group="${trigger.TRIGGER_GROUP}">删除</a>
                    <br>
                        <a href="" class="J-trigger" data-name="${trigger.JOB_NAME}" data-group="${trigger.JOB_GROUP}">立即触发</a>
                    <br>
                    <a href="" class="J-edit-modal" data-toggle="modal" data-target="#triggerModal" data-cron="${trigger.CRON_EXPRESSION}"
                     data-job="${trigger.JOB_NAME}" data-jobgroup="${trigger.JOB_GROUP}" data-name="${trigger.TRIGGER_NAME}" data-triggergroup="${trigger.TRIGGER_GROUP}">
                     修改</a>
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
        <h4 class="modal-title" id="myModalLabel">修改定时时间</h4>
      </div>
      <div class="modal-body">
        <input type="text" class="J-cron form-control"/>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary J-edit">Save changes</button>
      </div>
    </div>
  </div>
</div>
</body>

<script type="text/javascript" src="../js/trigger.js"></script>
</html>
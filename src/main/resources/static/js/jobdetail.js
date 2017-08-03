$(function(){
    $('.J-save-modal').on('click', function(e) {
        e.preventDefault();
        var $this = $(this);
        var $JCron = $('.J-cron');
        $JCron.val($this.data('cron'));
        var job = $this.data('job');
        $JCron.data('job', job);
        $JCron.data('jobgroup', $this.data('jobgroup'));
        $('.J-trigger').val(job);
    });

    $('.J-save').on('click', function(e){
        e.preventDefault();
        var triggerName = $('.J-trigger').val();
        var cron = $('.J-cron').val();
        var triggerGroup = $('.J-group').val();
        var jobName = $('.J-cron').data('job');
        var jobGroup = $('.J-cron').data('jobgroup');
        if(!triggerGroup || !triggerName || !cron) {
            alert('请完善表单');
            return false;
        }

        $.ajax({
            'method' : 'POST',
            'url' : $(this).data('url'),
            data : {
                "triggerName" : triggerName,
                "triggerGroup" : triggerGroup,
                "jobName" : jobName,
                "jobGroup" : jobGroup,
                "cron" : cron
            }
        }).done(function(resp){
            if (resp == 'invalid') {
                alert('cron 表达式不合法哦');
                return false;
            } else if(resp) {
                location.reload();
            } else {
                 alert("更新出现异常");
            }
        });
    });
});
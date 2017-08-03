$(function(){

    /**
    * 暂停触发器
    **/
    $('.J-pause').on('click', function(e){
        e.preventDefault();
        var $this = $(this);
        var name = $this.data('name');
        var group = $this.data('group');

        if (!name || !group) {
            return false;
        }
        $this.attr('disabled', true);

        $.ajax({
            method: "POST",
            url: 'pause',
            data: {
                'triggerName' : name,
                'groupName' : group
            }
        }).done(function(resp){
            if (!resp) {
                alert('暂停触发器失败');
                return false;
            }
            location.reload();
        }).fail(function(){
        })
    });

    /**
    * 恢复暂停的触发器
    **/
    $('.J-resume').on('click', function(e){
        e.preventDefault();
        var $this = $(this);
        var name = $this.data('name');
        var group = $this.data('group');

        if (!name || !group) {
            return false;
        }
        $this.attr('disabled', true);

        $.ajax({
            method: "POST",
            url: 'resume',
            data: {
                'triggerName' : name,
                'groupName' : group
            }
        }).done(function(resp){
            if (!resp) {
                alert('恢复触发器失败');
                return false;
            }
            location.reload();
        }).fail(function(){
        })
    });

    /**
    * 移除触发器
    **/
    $('.J-remove').on('click', function(e){
        e.preventDefault();
        var $this = $(this);
        var name = $this.data('name');
        var group = $this.data('group');

        if (confirm("确认删除该触发器吗?")) {
            if (!name || !group) {
                return false;
            }
            $this.attr('disabled', true);

            $.ajax({
                method: "POST",
                url: 'remove',
                data: {
                    'triggerName' : name,
                    'groupName' : group
                }
            }).done(function(resp){
                if (!resp) {
                    alert('移除触发器失败');
                    return false;
                }
                location.reload();
            }).fail(function(){
            })
        }
    });

    /**
    ** 立即触发触发器
    **/
    $('.J-trigger').on('click', function(e){
            e.preventDefault();
            var $this = $(this);
            var name = $this.data('name');
            var group = $this.data('group');

            if (confirm("确认立即触发该触发器吗?")) {
                if (!name || !group) {
                    return false;
                }
                $this.attr('disabled', true);

                $.ajax({
                    method: "POST",
                    url: '/cron/jobDetail/trigger',
                    data: {
                        'jobName' : name,
                        'groupName' : group
                    }
                }).done(function(resp){
                    if (!resp) {
                        alert('触发触发器失败');
                        return false;
                    }
                    location.reload();
                }).fail(function(){
                })
            }
        });

    $('.J-edit-modal').on('click', function(e) {
        e.preventDefault();
        var $this = $(this);
        var $JCron = $('.J-cron');
        $JCron.val($this.data('cron'));
        $JCron.data('job', $this.data('job'));
        $JCron.data('jobgroup', $this.data('jobgroup'));
        $JCron.data('name', $this.data('name'));
        $JCron.data('triggergroup', $this.data('triggergroup'));
    });

    $('.J-edit').on('click', function(e){
        e.preventDefault();
        var $JCron = $('.J-cron');
        var triggerName = $JCron.data('name');
        var triggerGroup = $JCron.data('triggergroup');
        var jobName = $JCron.data('job');
        var jobGroup = $JCron.data('jobgroup');
        var cron = $JCron.val();
        $.ajax({
            'method' : 'POST',
            'url' : 'edit',
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
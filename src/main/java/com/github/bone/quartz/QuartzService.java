package com.github.bone.quartz;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.List;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.bone.dao.QuartzDao;

/**
 * Created by mike on 16/12/29.
 */

@Component
public class QuartzService {

    private static final Logger logger = LoggerFactory.getLogger(QuartzService.class);

    @Autowired
    private Scheduler scheduler;
    @Autowired
    private QuartzDao quartzDao;

    public boolean update(String jobName, String jobGroup, String triggerName, String triggerGroup, String cron) {
        JobKey jobKey = new JobKey(jobName, jobGroup);
        JobDetail jobDetail;
        try {
            jobDetail = scheduler.getJobDetail(jobKey);
            Trigger trigger = newTrigger().withIdentity(triggerName, triggerGroup)
                    .forJob(jobDetail)
                    .withSchedule(cronSchedule(cron))
                    .startNow()
                    .build();
            if (scheduler.checkExists(trigger.getKey())) {
                scheduler.rescheduleJob(trigger.getKey(), trigger);
            } else {
                scheduler.scheduleJob(trigger);
            }
        } catch (SchedulerException e) {
            logger.error("update trigger fail, {}", e);
            return false;
        }
        return true;
    }

    public boolean removeTrigger(String triggerName, String triggerGroupName) {
        TriggerKey key = new TriggerKey(triggerName, triggerGroupName);
        try {
            scheduler.pauseTrigger(key);
            scheduler.unscheduleJob(key);
        } catch (SchedulerException e) {
            logger.error("remove trigger error, {}", e);
            return false;
        }
        return true;
    }

    public boolean pauseTrigger(String triggerName, String groupName) {
        TriggerKey key = new TriggerKey(triggerName, groupName);

        try {
            scheduler.pauseTrigger(key);
        } catch (SchedulerException e) {
            logger.error("pause trigger error, {}", e);
            return false;
        }
        return true;
    }

    public boolean resumeTrigger(String triggerName, String groupName) {
        TriggerKey key = new TriggerKey(triggerName, groupName);
        try {
            scheduler.resumeTrigger(key);
        } catch (SchedulerException e) {
            logger.error("resume trigger error, {}", e);
            return false;
        }
        return true;
    }

    public boolean pauseJob(String jobDetail, String groupName) {
        JobKey jobKey = new JobKey(jobDetail, groupName);
        try {
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            logger.error("pause job error, {}", e);
            return false;
        }
        return true;
    }

    public boolean resumeJob(String jobDetail, String groupName) {
        JobKey jobKey = new JobKey(jobDetail, groupName);
        try {
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            logger.error("resume job error, {}", e);
            return false;
        }
        return true;
    }

    public boolean triggerJob(String jobDetail, String groupName) {
        JobKey jobKey = new JobKey(jobDetail, groupName);
        try {
            scheduler.triggerJob(jobKey);
        } catch (SchedulerException e) {
            logger.error("trigger job fail, {}", e);
            return false;
        }
        return true;
    }

    public List triggers(String triggerName, String jobName, String jobGroup) {
        return quartzDao.findTriggers(triggerName, jobName, jobGroup);
    }

    public List jobDetails(String jobDetailName) {
        return quartzDao.findJobDetails(jobDetailName);
    }

    public List logs() {
        return quartzDao.findLogs();
    }
}


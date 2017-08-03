package com.github.bone.common.config;

import javax.sql.DataSource;

import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.github.bone.common.job.MethodExecutingJobDetailFactoryBean;


@Configuration
public class QuartzConfig {

    private static final String SCHEDULER_GROUP_NAME = "CRON_GROUP";

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(DataSource dataSource, TaskExecutor taskExecutor, JobDetail... jobDetails) throws SchedulerException {
        SchedulerFactoryBean quartzScheduler = new SchedulerFactoryBean();
        quartzScheduler.setConfigLocation(new ClassPathResource("quartz.properties"));
        quartzScheduler.setSchedulerName("scheduler");
        quartzScheduler.setApplicationContextSchedulerContextKey("applicationContext");
        quartzScheduler.setDataSource(dataSource);
        quartzScheduler.setTaskExecutor(taskExecutor);
        quartzScheduler.setJobDetails(jobDetails);
        return quartzScheduler;
    }

    @Bean(name = "executeJob")
    public MethodExecutingJobDetailFactoryBean beerProRatingConvert() {
        return jobDetailFactoryBean("jobService", "jobMethod", SCHEDULER_GROUP_NAME);
    }

    private MethodExecutingJobDetailFactoryBean jobDetailFactoryBean(String beanName, String methodName, String groupName) {
        MethodExecutingJobDetailFactoryBean jobDetail = new MethodExecutingJobDetailFactoryBean();
        jobDetail.setName(beanName + "#" + methodName);
        jobDetail.setGroup(groupName);
        jobDetail.setTargetBeanName(beanName);
        jobDetail.setTargetMethodName(methodName);
        jobDetail.setDurability(true);
        return jobDetail;
    }
}

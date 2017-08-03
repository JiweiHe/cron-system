package com.github.bone.common.job;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class MethodExecutingJobDetailFactoryBean extends JobDetailFactoryBean {
    private String targetBeanName;
    private String targetMethodName;

    @Override
    public void afterPropertiesSet() {
        setJobClass(MethodExecutingJob.class);
        getJobDataMap().put("beanName", targetBeanName);
        getJobDataMap().put("methodName", targetMethodName);
        super.afterPropertiesSet();
    }

    /**
     * Set the name of the target bean in the Spring BeanFactory.
     */
    public void setTargetBeanName(String targetBeanName) {
        this.targetBeanName = targetBeanName;
    }

    /**
     * Set the name of the target method in target bean.
     */
    public void setTargetMethodName(String targetMethodName) {
        this.targetMethodName = targetMethodName;
    }

    @PersistJobDataAfterExecution
    @DisallowConcurrentExecution
    public static class MethodExecutingJob extends QuartzJobBean {

        private ApplicationContext applicationContext;

        private String beanName;

        private String methodName;

        private transient Method methodObject;

        @Override
        protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
            Object bean = applicationContext.getBean(beanName);
            if (methodObject == null) {
                synchronized (this) {
                    if (methodObject == null) {
                        try {
                            methodObject = bean.getClass().getMethod(methodName);
                        } catch (NoSuchMethodException e) {
                            throw new IllegalArgumentException(methodName + " cannot be found in " + beanName);
                        }
                    }
                }
            }

            try {
                methodObject.invoke(bean);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new JobExecutionException(e);
            }
        }

        public void setApplicationContext(ApplicationContext applicationContext) {
            this.applicationContext = applicationContext;
        }

        public void setBeanName(String beanName) {
            this.beanName = beanName;
        }

        public void setMethodName(String methodName) {
            this.methodName = methodName;
        }
    }
}


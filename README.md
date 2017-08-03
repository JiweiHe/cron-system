定时任务管理系统

项目描述: 可通过后台灵活控制定时任务的执行时间,执行频率,是否终止。

项目架构: springboot + quartz

项目实现: 只要在QuartzConfig中注入一个MethodExecutingJobDetailFactoryBean, 指明需要执行的bean 以及 method 名称
        然后登陆后台,便可随意配置改Job的执行的时间点

项目运行: 参考application.yml配置文件,创建数据库,修改用户名密码为运行机器上的用户名密码
         执行 doc/quartz.sql  然后启动即可。后台的登陆账户密码为:dev 123456

其他: 定时代码可根据业务需要选是写在当前项目中还是暴露接口由管理系统调用,目前采用的是第一种
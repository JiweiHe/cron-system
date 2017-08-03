package com.github.bone.dao;




import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by mike on 16/12/29.
 */

@Repository
public class QuartzDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public List findTriggers(String triggerName, String jobName, String jobGroup) {
        MapSqlParameterSource map = new MapSqlParameterSource();
        String sql = "select * from QRTZ_TRIGGERS a, QRTZ_CRON_TRIGGERS c where a.TRIGGER_NAME= c.TRIGGER_NAME and a.TRIGGER_GROUP = c.TRIGGER_GROUP";
        if (isNotEmpty(triggerName)) {
            map.addValue("triggerName", "%" + triggerName + "%");
            sql = sql + " and " + "a.TRIGGER_NAME like :triggerName";
        }
        if (isNotEmpty(jobName)) {
            map.addValue("jobName", jobName);
            sql = sql + " and JOB_NAME = :jobName";
        }
        if (isNotEmpty(jobGroup)) {
            map.addValue("jobGroup", jobGroup);
            sql = sql + " and JOB_GROUP = :jobGroup";
        }
        return jdbcTemplate.queryForList(sql, map);
    }

    public List findJobDetails(String jobDetailName) {
        MapSqlParameterSource map = new MapSqlParameterSource();
        String sql = "select d.JOB_NAME as JOB_NAME, d.JOB_GROUP as JOB_GROUP, d.JOB_CLASS_NAME as JOB_CLASS_NAME, " +
                " d.IS_DURABLE as IS_DURABLE, d.IS_UPDATE_DATA as IS_UPDATE_DATA, d.IS_NONCONCURRENT as IS_NONCONCURRENT, d.REQUESTS_RECOVERY as REQUESTS_RECOVERY, " +
                " s.TRIGGER_NAME as TRIGGER_NAME, s.TRIGGER_GROUP as TRIGGER_GROUP " +
                " from QRTZ_JOB_DETAILS d left join QRTZ_TRIGGERS s on d.JOB_NAME = s.JOB_NAME and d.JOB_GROUP = s.JOB_GROUP ";
        if (isNotEmpty(jobDetailName)) {
            map.addValue("jobDetailName", jobDetailName);
            sql = sql + " and d.JOB_NAME like %:jobDetailName%";
        }
        return jdbcTemplate.queryForList(sql, map);
    }

    public List findLogs() {
        return jdbcTemplate.queryForList("select * from QRTZ_TRIGGER_MISFIRED_LOG", new MapSqlParameterSource());
    }
}

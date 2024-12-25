package com.telecom.job.custom;

import com.telecom.manage.entity.Job.OperateState;
import com.telecom.manage.service.JobService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

public class TestJob implements Job {

    @Autowired
    JobService jobService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext)
            throws JobExecutionException {

        System.out.println("执行quartz任务" + System.currentTimeMillis());
        String jobName = jobExecutionContext.getJobDetail().getKey().getName();
        jobService.updateState(OperateState.executed.toString(), jobName);

    }
}

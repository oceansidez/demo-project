package com.telecom.job.system;

import com.telecom.base.BaseSchedulTask;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

@Component
public class TestCronTask extends BaseSchedulTask {

    // 每天0点到2点每隔20分钟执行一次
    @Value(value = "0 0/20 0-2 * * ?")
    private String cron;

    @Override
    public void exec() {
        System.out.println("这是一条cron型定时任务");
    }

    @Override
    public Trigger getCustomTrigger() {
        return new CronTrigger(this.cron);
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

}

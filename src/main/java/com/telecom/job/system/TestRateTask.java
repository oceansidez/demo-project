package com.telecom.job.system;

import com.telecom.base.BaseSchedulTask;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Component;

@Component
public class TestRateTask extends BaseSchedulTask {

    // 每隔3600秒（1小时）执行一次
    @Value(value = "3600000")
    private long rate;

    @Override
    public void exec() {
        System.out.println("这是一条rate型定时任务");
    }

    @Override
    public Trigger getCustomTrigger() {
        return new PeriodicTrigger(this.rate);
    }

    public long getRate() {
        return rate;
    }

    public void setRate(long rate) {
        this.rate = rate;
    }

}

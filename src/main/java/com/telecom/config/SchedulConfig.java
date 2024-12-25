package com.telecom.config;

import com.telecom.job.system.TestCronTask;
import com.telecom.job.system.TestRateTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * 定时任务配置
 *
 */
@Configuration
public class SchedulConfig implements SchedulingConfigurer {

	@Autowired
	TestRateTask trt;
	
	@Autowired
	TestCronTask tct;

	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		 taskRegistrar.addTriggerTask(trt, trt.getTrigger());
		 taskRegistrar.addTriggerTask(tct, tct.getTrigger());
	}
}
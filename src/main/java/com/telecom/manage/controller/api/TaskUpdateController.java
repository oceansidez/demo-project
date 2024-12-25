package com.telecom.manage.controller.api;

import com.telecom.job.system.TestCronTask;
import com.telecom.job.system.TestRateTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/taskUpdate")
public class TaskUpdateController {

	@Autowired
	TestRateTask trt;
	
	@Autowired
	TestCronTask tct;
	
	@GetMapping("/rate")
	public String rate() {
		trt.setRate(1000);
		// 本处理更新后，下一次任务执行会按照原定时规则执行
		// 下下次才会按照新规则执行
		return "update rate task";
	}

	@GetMapping("/cron")
	public String cron() {
		tct.setCron("0 0/10 9-10 * * ?");
		// 本处理更新后，下一次任务执行会按照原定时规则执行
		// 下下次才会按照新规则执行
		return "update cron task";
	}
	
}

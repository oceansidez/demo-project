package com.telecom.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * 线程池配置
 * 
 * 说明：
 * ThreadPoolTaskExecutor运行策略
 * 1.当一个任务被提交到线程池时，首先查看线程池的核心线程是否都在执行任务，否就选择一条线程执行任务，是就执行第二步。
 * 2.查看核心线程池是否已满，不满就创建一条线程执行任务，否则执行第三步。
 * 3.查看任务队列是否已满，不满就将任务存储在任务队列中，否则执行第四步。
 * 4.查看线程池是否已满，不满就创建一条线程执行任务，否则就按照策略处理无法执行的任务。
 * 
 * 底层ThreadPoolExecutor中表现:
 * 1.如果当前运行的线程数小于corePoolSize，那么就创建线程来执行任务（执行时需要获取全局锁）。
 * 2.如果运行的线程大于或等于corePoolSize，那么就把task加入BlockQueue。
 * 3.如果创建的线程数量大于BlockQueue的最大容量，那么创建新线程来执行该任务。
 * 4.如果创建线程导致当前运行的线程数超过maximumPoolSize，就根据饱和策略来拒绝该任务。
 *
 */

@Configuration
@EnableAsync
public class ExecutorConfig {
	
	@Bean
	public Executor asyncServiceExecutor() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(10);
        taskExecutor.setMaxPoolSize(50);
        taskExecutor.setQueueCapacity(200);
        taskExecutor.setKeepAliveSeconds(60);
        taskExecutor.setThreadNamePrefix("taskExecutor--");
        taskExecutor.setWaitForTasksToCompleteOnShutdown(false);
        taskExecutor.setAwaitTerminationSeconds(60);
        return taskExecutor;
	}
}

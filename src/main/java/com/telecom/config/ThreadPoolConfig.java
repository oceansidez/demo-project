package com.telecom.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池配置
 *
 */
@Configuration
@EnableAsync
@PropertySource(value = "classpath:/application-threadpool.properties")
public class ThreadPoolConfig {
	// 默认情况下，在创建了线程池后，线程池中的线程数为0，当有任务来之后，就会创建一个线程去执行任务，
	// 当线程池中的线程数目达到corePoolSize后，就会把到达的任务放到缓存队列当中
	// 当队列满了，就继续创建线程，当线程数量大于等于maxPoolSize后，开始使用拒绝策略拒绝

	@Value("${thread-pool.commonCorePoolSize}")
	private int commonCorePoolSize;
	@Value("${thread-pool.commonMaxPoolSize}")
	private int commonMaxPoolSize;
	@Value("${thread-pool.commonKeepAliveTime}")
	private int commonKeepAliveTime;
	@Value("${thread-pool.commonQueueCapacity}")
	private int commonQueueCapacity;
	@Value("${thread-pool.commonThreadNamePrefix}")
	private String commonThreadNamePrefix;

	@Bean("commonThreadPool")
	public ThreadPoolTaskExecutor commonTaskExecutor() {
		// 配置参数
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(commonCorePoolSize);
		executor.setMaxPoolSize(commonMaxPoolSize);
		executor.setKeepAliveSeconds(commonKeepAliveTime);
		executor.setQueueCapacity(commonQueueCapacity);
		executor.setThreadNamePrefix(commonThreadNamePrefix);

		// 线程池对拒绝任务的处理策略
		// CallerRunsPolicy：由调用线程（提交任务的线程）处理该任务
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		
		// 初始化
		executor.initialize();
		return executor;
	}
	
	@Value("${thread-pool.myCorePoolSize}")
	private int myCorePoolSize;
	@Value("${thread-pool.myMaxPoolSize}")
	private int myMaxPoolSize;
	@Value("${thread-pool.myKeepAliveTime}")
	private int myKeepAliveTime;
	@Value("${thread-pool.myQueueCapacity}")
	private int myQueueCapacity;
	@Value("${thread-pool.myThreadNamePrefix}")
	private String myThreadNamePrefix;
	
	@Bean("myThreadPool")
	public ThreadPoolTaskExecutor myTaskExecutor() {
		// 配置参数
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(myCorePoolSize);
		executor.setMaxPoolSize(myMaxPoolSize);
		executor.setKeepAliveSeconds(myKeepAliveTime);
		executor.setQueueCapacity(myQueueCapacity);
		executor.setThreadNamePrefix(myThreadNamePrefix);

		// 线程池对拒绝任务的处理策略
		// CallerRunsPolicy：由调用线程（提交任务的线程）处理该任务
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		
		// 初始化
		executor.initialize();
		return executor;
	}
}

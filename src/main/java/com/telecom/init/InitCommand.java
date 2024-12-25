package com.telecom.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 应用启动时执行的命令执行 作用：初始化
 *
 */
@Component
@Order(value = 1)
public class InitCommand implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
		System.out.println("应用启动执行命令---init");
	}

}
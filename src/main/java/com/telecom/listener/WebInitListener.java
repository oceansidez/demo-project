package com.telecom.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;


/**
 * 监听系统启动与关闭Listener
 *
 */
@WebListener
public class WebInitListener implements ServletContextListener {

	/**
	 * 初始化方法
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("应用已启动");
	}
 
	/**
	 * 销毁方法
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("应用已停止");
	}

}

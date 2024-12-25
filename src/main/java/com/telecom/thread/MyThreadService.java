package com.telecom.thread;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Async(value = "myThreadPool")
public class MyThreadService {

	public void test1() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("线程：【"+ Thread.currentThread().getName() +"】test1方法休息5秒");
	}
	
	public void test2() {
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("线程：【"+ Thread.currentThread().getName() +"】test2方法休息8秒");
	}

}

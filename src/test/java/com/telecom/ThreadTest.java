package com.telecom;

import com.telecom.thread.MyThreadService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ThreadTest extends SpringBootBaseTest {

    @Autowired
    private MyThreadService myThreadService;


    @Test
    public void  test(){
        System.out.println("myThreadService = " + myThreadService);
    }

    @Test
    public void testThread() {
        myThreadService.test1();
        myThreadService.test2();
    }

}

package com.telecom.thread;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
@Async(value = "commonThreadPool")
public class CommonThreadService {

}

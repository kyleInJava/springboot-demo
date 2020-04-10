package com.kyle.demo.task;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class TaskDemo {
	
	//每10分钟执行一次
	@Scheduled(fixedRate=10*60*1000)
    public void refreshAccessToken(){
		System.out.println("hello world");
    }

}

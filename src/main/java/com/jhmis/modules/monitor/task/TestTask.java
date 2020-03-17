package com.jhmis.modules.monitor.task;

import org.quartz.DisallowConcurrentExecution;

import com.jhmis.modules.monitor.entity.Task;

@DisallowConcurrentExecution  
public class TestTask extends Task{

	@Override
	public void run() {
		System.out.println("这是测试任务TestTask。");
		
	}

}

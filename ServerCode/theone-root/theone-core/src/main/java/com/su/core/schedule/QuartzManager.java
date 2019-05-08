package com.su.core.schedule;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuartzManager {
	
	private static Logger logger = LoggerFactory.getLogger(QuartzManager.class);
	
	private static Scheduler scheduler;
	
	public static void start() {
		try {
			SchedulerFactory factory = new StdSchedulerFactory("com/su/core/quartz.properties");
			scheduler = factory.getScheduler();
			scheduler.start();
			logger.info("启动 quartz 定时任务");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void stop() {
		try {
			scheduler.shutdown();
			logger.info("关闭 quartz 定时任务");
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

}

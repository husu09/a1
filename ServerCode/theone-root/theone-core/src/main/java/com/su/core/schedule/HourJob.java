package com.su.core.schedule;

import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.su.common.util.SpringUtil;
import com.su.core.akka.EventActorType;
import com.su.core.context.BaseUserContext;
import com.su.core.context.BaseGameContext;

public class HourJob implements Job {
	
	private BaseGameContext gameContext;
	
	public HourJob() {
		gameContext = SpringUtil.getContext().getBean(BaseGameContext.class);
	}
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		Map<Long, BaseUserContext> userContextMap = gameContext.getUserContextMap();
		for (BaseUserContext userCtx : userContextMap.values()) {
			if (userCtx.isOnline())
				userCtx.getActor().call(EventActorType.HOUR.getValue(), userCtx);
		}
	}

}

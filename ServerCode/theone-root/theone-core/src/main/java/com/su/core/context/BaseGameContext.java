package com.su.core.context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.su.core.akka.BaseActor;

/**
 * 存储所有在线用户
 */
public class BaseGameContext {
	
	/**
	 * 是否接受请求
	 * */
	private volatile boolean accept;
	
	/**
	 * 在线玩家
	 * */
	private Map<Long, BaseUserContext> userContextMap = new ConcurrentHashMap<>();
	

	public <T extends BaseUserContext> Map<Long, T> getUserContextMap() {
		return (Map<Long, T>) userContextMap;
	}
	
	public <T extends BaseUserContext> T getUser(long id) {
		return (T) userContextMap.get(id);
	}
	
	public <T extends BaseUserContext> T getUser(long id, Class<BaseUserContext> cls, Class<BaseActor> actorClass)  {
		BaseUserContext user = userContextMap.get(id);
		if (user == null) {
			try {
				   user = (BaseUserContext) cls.newInstance();
				   user.setActor((BaseActor) actorClass.newInstance());
				   BaseUserContext currUser = userContextMap.putIfAbsent(id, user);
				   if (currUser != null ) {
					   user = currUser;
				   }
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return (T) user;
	}

	public boolean isAccept() {
		return accept;
	}

	public void setAccept(boolean accept) {
		this.accept = accept;
	}
	
	
	
	
		
}

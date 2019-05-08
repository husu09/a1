package com.su.core.event;

import com.google.protobuf.MessageLite;
import com.su.core.context.BaseUserContext;

public interface IGameEvent<T extends BaseUserContext, B extends MessageLite> {
	/**
	 * 服务启动
	 */
	public void serverStart();

	/**
	 * 服务关闭
	 */
	public void serverStop();

	/**
	 * 服务数据每日重置
	 */
	public void zero();

	/**
	 * 玩家数据每日重置
	 */
	public void zero(T t);
	
	/**
	 * 系统整点
	 * */
	public void hour();
	
	/**
	 * 玩家整点
	 * */
	public void hour(T t);

	/**
	 * 登录
	 */
	public void login(T t, B b);

	/**
	 * 登出
	 */
	public void logout(T t);
}

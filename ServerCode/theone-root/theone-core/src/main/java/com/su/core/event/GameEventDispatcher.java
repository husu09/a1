
package com.su.core.event;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.google.protobuf.MessageLite;
import com.su.core.context.BaseUserContext;

@Component
public class GameEventDispatcher<T extends BaseUserContext, B extends MessageLite> implements IGameEvent<T, B> {

	private List<IGameEvent> gameEventHandlers = new ArrayList<>();

	/**
	 * 注册到事件分发器
	 */
	public void register(IGameEvent gameEvent) {
		gameEventHandlers.add(gameEvent);
	}

	@Override
	public void serverStart() {
		for (IGameEvent gameEvent : gameEventHandlers)
			gameEvent.serverStart();
	}

	@Override
	public void serverStop() {
		for (IGameEvent gameEvent : gameEventHandlers)
			gameEvent.serverStop();
	}

	@Override
	public void zero() {
		for (IGameEvent gameEvent : gameEventHandlers)
			gameEvent.zero();
	}

	@Override
	public void zero(T t) {
		for (IGameEvent gameEvent : gameEventHandlers)
			gameEvent.zero(t);
	}

	@Override
	public void login(T t, B b) {
		for (IGameEvent gameEvent : gameEventHandlers)
			gameEvent.login(t, b);
	}

	@Override
	public void logout(T t) {
		for (IGameEvent gameEvent : gameEventHandlers)
			gameEvent.logout(t);
	}

	@Override
	public void hour() {
		for (IGameEvent gameEvent : gameEventHandlers)
			gameEvent.hour();
	}

	@Override
	public void hour(T t) {
		for (IGameEvent gameEvent : gameEventHandlers)
			gameEvent.hour(t);
	}

	

}

package com.su.core.event;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.protobuf.MessageLite;
import com.su.core.context.BaseUserContext;

@Component
public class BaseGameEvent<T extends BaseUserContext, B extends MessageLite> implements IGameEvent<T, B> {

	@Autowired
	private GameEventDispatcher gameEventDispatcher;

	@PostConstruct
	private void init() {
		gameEventDispatcher.register(this);
	}

	@Override
	public void serverStart() {
		
	}

	@Override
	public void serverStop() {
		
	}

	@Override
	public void zero() {
		
	}

	@Override
	public void zero(T t) {
		
	}

	@Override
	public void hour() {
		
	}

	@Override
	public void hour(T t) {
		
	}

	@Override
	public void login(T t, B b) {
		
	}

	@Override
	public void logout(T t) {
		
	}
	
}

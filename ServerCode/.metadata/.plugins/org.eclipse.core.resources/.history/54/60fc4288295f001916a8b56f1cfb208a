package com.su.core.context;

import com.su.core.akka.BaseActor;

import io.netty.channel.ChannelHandlerContext;

public class BaseUserContext {
	private BaseActor actor;
	private ChannelHandlerContext ctx;
	private Long id;

	public boolean isOnline() {
		if (ctx != null) {
			return true;
		}
		return false;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ChannelHandlerContext getCtx() {
		return ctx;
	}

	public void setCtx(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}

	public void setActor(BaseActor actor) {
		this.actor = actor;
	}

	public BaseActor getActor() {
		return actor;
	}

}

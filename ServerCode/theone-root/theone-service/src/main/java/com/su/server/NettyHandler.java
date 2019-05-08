package com.su.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.su.core.akka.AkkaUtil;
import com.su.core.netty.INettyHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

@Component
public class NettyHandler implements INettyHandler {
	
	private Logger logger = LoggerFactory.getLogger(NettyHandler.class);
	
	@Autowired
	private GameContext gameContext;
	
	@Override
	public boolean isAccept() {
		return gameContext.isAccept();
	}
	
	public static final AttributeKey<Long> PLAYER_ID = AttributeKey.valueOf("PLAYER_ID");
	
	@Override
	public void receiveMessage(ChannelHandlerContext ctx, Object msg) {
		Attribute<Long> attr = ctx.channel().attr(PLAYER_ID);
		Long playerId = attr.get();
		if (playerId != null) {
			PlayerContext playerContext = gameContext.getUser(playerId);
			if (playerContext == null) {
				logger.error("playerContext is null {}", playerId);
				return;
			}
			playerContext.getActor().call(PlayerActorType.PROCESS.getValue(), ctx, msg);
		} else {
			PLayerActor playerActor = AkkaUtil.createActor(PLayerActor.class);
			playerActor.call(PlayerActorType.PROCESS.getValue(), ctx, msg);
		}
	}

	@Override
	public void disconnect(ChannelHandlerContext ctx) {
		Attribute<Long> attr = ctx.channel().attr(PLAYER_ID);
		Long playerId = attr.get();
		if (playerId != null) {
			PlayerContext playerContext = gameContext.getUser(playerId);
			if (playerContext == null) {
				logger.error("playerContext is null {}", playerId);
				return;
			}
			// 退出事件
			playerContext.getActor().call(PlayerActorType.LOGOUT.getValue());
			// 从在线玩家中移除
			gameContext.getUserContextMap().remove(playerId);
			// 关闭 actor
			AkkaUtil.poisonPill(playerContext.getActor());
		}
	}

}

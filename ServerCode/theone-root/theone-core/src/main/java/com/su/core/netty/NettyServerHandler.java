/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.su.core.netty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Handles both client-side and server-side handler depending on which
 * constructor was called.
 */
@Sharable
@Component
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
	
	@Autowired
	private INettyHandler nettyHandler;
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		nettyHandler.receiveMessage(ctx, msg);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// 不接受请求，拒绝连接
		if (!nettyHandler.isAccept()) {
			ctx.close();
		}
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		nettyHandler.disconnect(ctx);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}


}

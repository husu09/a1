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
package com.su.client.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.su.client.proto.MyProtobufDecoder;
import com.su.client.proto.MyProtobufEncoder;
import com.su.client.proto.MyProtobufVarint32LengthFieldPrepender;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Modification of {@link EchoClient} which utilizes Java object serialization.
 */
@Component
public final class NettyClient {
	
	@Autowired
	private MyProtobufDecoder myProtobufDecoder;
	@Autowired
	private MyProtobufVarint32LengthFieldPrepender myProtobufVarint32LengthFieldPrepender;
	@Autowired
	private MyProtobufEncoder MyProtobufEncoder;
	@Autowired
	private NettyClientHandler nettyClientHandler;

	private EventLoopGroup group;

	public void start(String host, int port) {
		group = new NioEventLoopGroup();
		Bootstrap b = new Bootstrap();
		b.group(group)
		.channel(NioSocketChannel.class)
		.handler(new ChannelInitializer<SocketChannel>() {
			@Override
			public void initChannel(SocketChannel ch) throws Exception {
				ChannelPipeline p = ch.pipeline();
				p.addLast(myProtobufDecoder);
				p.addLast(myProtobufVarint32LengthFieldPrepender);
				p.addLast(MyProtobufEncoder);
				p.addLast(nettyClientHandler);
			}
		});

		// Start the connection attempt.

		try {
			b.connect(host, port).sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public void stop() {
		if (group != null) {
			group.shutdownGracefully();
		}
	}
}

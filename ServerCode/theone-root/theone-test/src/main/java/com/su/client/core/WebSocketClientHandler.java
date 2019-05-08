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
//The MIT License
//
//Copyright (c) 2009 Carl Bystršm
//
//Permission is hereby granted, free of charge, to any person obtaining a copy
//of this software and associated documentation files (the "Software"), to deal
//in the Software without restriction, including without limitation the rights
//to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//copies of the Software, and to permit persons to whom the Software is
//furnished to do so, subject to the following conditions:
//
//The above copyright notice and this permission notice shall be included in
//all copies or substantial portions of the Software.
//
//THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
//THE SOFTWARE.

package com.su.client.core;

import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.protobuf.MessageLite;
import com.su.client.handler.ReceiveMessageHandler;
import com.su.client.proto.ProtoContext;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.CorruptedFrameException;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketHandshakeException;
import io.netty.util.CharsetUtil;

@Component
@Sharable
public class WebSocketClientHandler extends SimpleChannelInboundHandler<Object> {
	
	private Logger logger = LoggerFactory.getLogger(WebSocketClientHandler.class);
	
	@Autowired
	private ReceiveMessageHandler receiveMessageHandler;

	@Autowired
	private ProtoContext protoContext;
	
	@Autowired
	private ClientContext clientContext;
	
    private WebSocketClientHandshaker handshaker;
    private ChannelPromise handshakeFuture;
    
    public void setHandshaker(WebSocketClientHandshaker handshaker) {
		this.handshaker = handshaker;
	}


	public ChannelFuture handshakeFuture() {
        return handshakeFuture;
    }
    
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        handshakeFuture = ctx.newPromise();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        handshaker.handshake(ctx.channel());
        clientContext.setCtx(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println("WebSocket Client disconnected!");
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        Channel ch = ctx.channel();
        if (!handshaker.isHandshakeComplete()) {
            try {
                handshaker.finishHandshake(ch, (FullHttpResponse) msg);
                System.out.println("WebSocket Client connected!");
                handshakeFuture.setSuccess();
            } catch (WebSocketHandshakeException e) {
                System.out.println("WebSocket Client failed to connect");
                handshakeFuture.setFailure(e);
            }
            return;
        }

        if (msg instanceof FullHttpResponse) {
            FullHttpResponse response = (FullHttpResponse) msg;
            throw new IllegalStateException(
                    "Unexpected FullHttpResponse (getStatus=" + response.status() +
                            ", content=" + response.content().toString(CharsetUtil.UTF_8) + ')');
        }

        WebSocketFrame frame = (WebSocketFrame) msg;
        if (frame instanceof BinaryWebSocketFrame) {
        	// 解码消息
        	// 解码消息
        	ByteBuf in = ((BinaryWebSocketFrame)frame).content();
        	
        	if (in.readableBytes() < 4) {
        		return;
        	}
            in.markReaderIndex();
            int length = in.readInt();
            
            if (length < 0) {
                throw new CorruptedFrameException("negative length: " + length);
            }

            if (in.readableBytes() < length) {
                in.resetReaderIndex();
            } else {
            	ByteBuf b = in.readRetainedSlice(length);
            	
            	// 协议名称
            	int nameLength = b.readInt();
            	String name = b.toString(b.readerIndex(), nameLength, Charset.forName("UTF-8"));
            	if (!protoContext.getMessageLiteMap().containsKey(name)) {
        			logger.error("not fined message is {}", name);
        			return;
        		}
            	// 协议数据
            	int dataLength = b.readInt();
            	byte[] data = new byte[dataLength];
            	b.readBytes(data);
            	
            	// 解析协议
            	MessageLite messageLite = protoContext.getMessageLiteMap().get(name).getDefaultInstanceForType()
        				.getParserForType().parseFrom(data);
        		
        		// 逻辑
        		receiveMessageHandler.process(messageLite);
            }
        } else {
        	 String message = "unsupported frame type: " + frame.getClass().getName();
             throw new UnsupportedOperationException(message);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        if (!handshakeFuture.isDone()) {
            handshakeFuture.setFailure(cause);
        }
        ctx.close();
    }
}

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

import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.protobuf.MessageLite;
import com.su.core.proto.ProtoContext;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.CorruptedFrameException;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

@Sharable
@Component
public class WebSocketHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketHandler.class);
    
    @Autowired
	private ProtoContext protoContext;
    
    @Autowired
	private INettyHandler nettyHandler;

	
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
        if (frame instanceof BinaryWebSocketFrame) {
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
            	ByteBuf msg = in.readRetainedSlice(length);
            	
            	// 协议名称
            	int nameLength = msg.readInt();
            	String name = msg.toString(msg.readerIndex(), nameLength, Charset.forName("UTF-8"));
            	if (!protoContext.getMessageLiteMap().containsKey(name)) {
        			logger.error("not fined message is {}", name);
        			return;
        		}
            	// 协议数据
            	int dataLength = msg.readInt();
            	byte[] data = new byte[dataLength];
            	msg.readBytes(data);
            	
            	// 解析协议
            	MessageLite messageLite = protoContext.getMessageLiteMap().get(name).getDefaultInstanceForType()
        				.getParserForType().parseFrom(data);
            	// 逻辑
            	nettyHandler.receiveMessage(ctx, messageLite);
            }
        } else {
            String message = "unsupported frame type: " + frame.getClass().getName();
            throw new UnsupportedOperationException(message);
        }
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

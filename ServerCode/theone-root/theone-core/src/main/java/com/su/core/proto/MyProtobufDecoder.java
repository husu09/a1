package com.su.core.proto;

import java.nio.charset.Charset;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.protobuf.MessageLite;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

@Sharable
@Component
public class MyProtobufDecoder extends MessageToMessageDecoder<ByteBuf> {
	
	private Logger logger = LoggerFactory.getLogger(MyProtobufDecoder.class);
	
	@Autowired
	private ProtoContext protoContext;

	@Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out)
            throws Exception {
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
    	
		out.add(messageLite);
    
    	
      
    }
}

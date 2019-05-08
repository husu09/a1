package com.su.client.proto;

import java.util.List;

import org.springframework.stereotype.Component;

import com.google.protobuf.MessageLite;
import com.google.protobuf.MessageLiteOrBuilder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

@Sharable
@Component
public class MyProtobufEncoder extends MessageToMessageEncoder<MessageLiteOrBuilder> {
	
    @Override
    protected void encode(ChannelHandlerContext ctx, MessageLiteOrBuilder msg, List<Object> out)
            throws Exception {
        // 协议名称
        String name = msg.getClass().getSimpleName();
        byte[] nameData = name.getBytes("UTF-8");
       // 协议数据
		byte[] data = null;
		if (msg instanceof MessageLite) {
			data = ((MessageLite) msg).toByteArray();
		}
		if (msg instanceof MessageLite.Builder) {
			data = ((MessageLite.Builder) msg).build().toByteArray();
		}
		ByteBuf b = Unpooled.buffer(nameData.length + data.length);
		b.writeInt(nameData.length);
		b.writeBytes(nameData);
		b.writeInt(data.length);
		b.writeBytes(data);
		
		out.add(b);
    }
}

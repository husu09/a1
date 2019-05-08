package com.su.client.proto;

import org.springframework.stereotype.Component;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;


@Sharable
@Component
public class MyProtobufVarint32LengthFieldPrepender extends MessageToByteEncoder<ByteBuf> {
	
	private static final int INT_LENGTH = 4;
	
    @Override
    protected void encode(
            ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) throws Exception {
        int bodyLen = msg.readableBytes();
        out.ensureWritable(INT_LENGTH + bodyLen);
        out.writeInt(bodyLen);
        out.writeBytes(msg, msg.readerIndex(), bodyLen);
    }

}

package com.su.core.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.su.core.proto.MyProtobufDecoder;
import com.su.core.proto.MyProtobufEncoder;
import com.su.core.proto.MyProtobufVarint32FrameDecoder;
import com.su.core.proto.MyProtobufVarint32LengthFieldPrepender;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

@Component
public final class NettyServer {

	private Logger logger = LoggerFactory.getLogger(NettyServer.class);

	@Value("${server.port}")
	private int port;

	@Autowired
	private MyProtobufDecoder myProtobufDecoder;
	@Autowired
	private MyProtobufVarint32LengthFieldPrepender myProtobufVarint32LengthFieldPrepender;
	@Autowired
	private MyProtobufEncoder MyProtobufEncoder;
	@Autowired
	private NettyServerHandler nettyServerHandler;
	

	private EventLoopGroup bossGroup;
	private EventLoopGroup workerGroup;
	private Channel channel;

	public void start() {

		bossGroup = new NioEventLoopGroup();
		workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
			.channel(NioServerSocketChannel.class)
					.handler(new LoggingHandler(LogLevel.INFO))
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch) throws Exception {
							ChannelPipeline p = ch.pipeline();
							p.addLast(new MyProtobufVarint32FrameDecoder());
							p.addLast(myProtobufDecoder);
							p.addLast(myProtobufVarint32LengthFieldPrepender);
							p.addLast(MyProtobufEncoder);
							p.addLast(nettyServerHandler);
						}
					});
			channel = b.bind(port).sync().channel();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("启动Netty服务 {}", port);
	}

	public void stop() {
		try {
			channel.close().sync();
		} catch (Exception e) {
			e.printStackTrace();
		}
		bossGroup.shutdownGracefully();
		workerGroup.shutdownGracefully();
		logger.info("关闭Netty服务");
	}

}

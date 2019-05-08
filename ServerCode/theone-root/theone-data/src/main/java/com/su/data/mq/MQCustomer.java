package com.su.data.mq;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

@Component
public class MQCustomer {

	private Logger logger = LoggerFactory.getLogger(MQCustomer.class);

	@Value("${mq.queueName}")
	private String queueName;
	@Value("${mq.host}")
	private String host;
	@Value("${mq.port}")
	private int port;

	private Connection connection;
	private Channel channel;

	@Autowired
	private CustomerWorker customerWorker;

	public void start() {
		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost(host);
			factory.setPort(port);
			// 获取连接
			connection = factory.newConnection();
			// 创建通道
			channel = connection.createChannel();
			channel.queueDeclare(queueName, true, false, false, null);
			channel.basicQos(1);

			DeliverCallback deliverCallback = (consumerTag, delivery) -> {
				String message = new String(delivery.getBody(), "UTF-8");
				try {
					customerWorker.doWork(message);
				} finally {
					channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
				}
			};
			channel.basicConsume(queueName, false, deliverCallback, consumerTag -> {
			});
			logger.info("启动RabbitMQ服务成功");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void stop() {
		if (channel != null) {
			try {
				channel.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		logger.info("关闭RabbitMQ服务");
	}
}

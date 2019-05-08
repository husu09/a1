package com.su.core.data.mq;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.su.common.mq.DataOperator;
import com.su.common.mq.MQMessage;
import com.su.common.util.JSONUtil;

/**
 * mq 服务对象
 * */
@Service
public class MQService {

	@Autowired
	private MQProducer mqClient;

	public <T> void save(T t) {
		MQMessage mqMessage = new MQMessage();
		mqMessage.setClassName(t.getClass().getCanonicalName());
		mqMessage.setMqOperator(DataOperator.SAVE);
		mqMessage.setData(JSONUtil.dataEncode(t));
		mqClient.produce(JSONUtil.dataEncode(mqMessage));
	}

	public <T> void save(Collection<T> ts) {
		for (T t : ts) {
			save(t);
		}

	}

	public <T> void save(T[] ts) {
		for (T t : ts) {
			save(t);
		}
	}

	public <T> void update(T t) {
		MQMessage mqMessage = new MQMessage();
		mqMessage.setClassName(t.getClass().getCanonicalName());
		mqMessage.setMqOperator(DataOperator.UPDATE);
		mqMessage.setData(JSONUtil.dataEncode(t));
		mqClient.produce(JSONUtil.dataEncode(mqMessage));
	}

	public <T> void update(Collection<T> ts) {
		for (T t : ts) {
			update(t);
		}
	}

	public <T> void update(T[] ts) {
		for (T t : ts) {
			update(t);
		}
	}

	public <T> void delete(T t) {
		MQMessage mqMessage = new MQMessage();
		mqMessage.setClassName(t.getClass().getCanonicalName());
		mqMessage.setMqOperator(DataOperator.DELETE);
		mqMessage.setData(JSONUtil.dataEncode(t));
		mqClient.produce(JSONUtil.dataEncode(mqMessage));
	}

	public <T> void delete(Collection<T> ts) {
		for (T t : ts) {
			delete(t);
		}

	}

	public <T> void delete(T[] ts) {
		for (T t : ts) {
			delete(t);
		}
	}
	
	public <T> void common(DataOperator dataOperator, T t) {
		MQMessage mqMessage = new MQMessage();
		mqMessage.setClassName(t.getClass().getCanonicalName());
		mqMessage.setMqOperator(dataOperator);
		mqMessage.setData(JSONUtil.dataEncode(t));
		mqClient.produce(JSONUtil.dataEncode(mqMessage));
	}
	
}
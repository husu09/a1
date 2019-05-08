package com.su.config.mapper;
import org.springframework.stereotype.Component;
import java.util.LinkedHashMap;
import com.fasterxml.jackson.core.type.TypeReference;
import com.su.common.util.JSONUtil;
import com.su.config.co.ParamterCo;
///start
///end
@Component
public class ParamterConf extends BaseMapper<ParamterCo> {

	@Override
	public void mapper(String str) {
		storageMap = JSONUtil.formatDecode(str, new TypeReference<LinkedHashMap<Integer, ParamterCo>>(){});
	}

	///start
	///end
}
package com.su.config.mapper;
import org.springframework.stereotype.Component;
import java.util.LinkedHashMap;
import com.fasterxml.jackson.core.type.TypeReference;
import com.su.common.util.JSONUtil;
import com.su.config.co.ContestRewardCo;
///start
///end
@Component
public class ContestRewardConf extends BaseMapper<ContestRewardCo> {

	@Override
	public void mapper(String str) {
		storageMap = JSONUtil.formatDecode(str, new TypeReference<LinkedHashMap<Integer, ContestRewardCo>>(){});
	}

	///start
	///end
}
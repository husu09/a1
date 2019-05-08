package com.su.config.mapper;

import java.util.Collection;
import java.util.Map;



/**
 * 配置文件映射基类
 */
public abstract class BaseMapper<T> {

	/**
	 * 存储解析后的数据
	 */
	protected Map<Integer, T> storageMap;

	public T get(int id) {
		return storageMap.get(id);
	}

	public Collection<T> all() {
		return storageMap.values();
	}
	
	public void finishLoad() {
		
	}
	
	public abstract void mapper(String str); 

}

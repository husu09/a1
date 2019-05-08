package com.su.common.util;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.databind.type.SimpleType;


public class JSONUtil {
	
	private static final ObjectMapper dataMapper;
	
	/**
	 * 使用@JsonFilter注解
	 * */
	@JsonFilter("dataFilter")
	public static class DataFilter {
		
	}
	
	static {
		dataMapper = new ObjectMapper();
		// 包括所有属性
		dataMapper.setVisibility(PropertyAccessor.ALL, Visibility.NONE);
		dataMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		//序列化空对象不报错
		dataMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		//反序列化遇到未知属性不报错
		dataMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		// 自定义的属性过滤器
		FilterProvider filterProvider = new SimpleFilterProvider().addFilter("dataFilter", new SimpleBeanPropertyFilter() {
			@Override
			public void serializeAsField(Object pojo, JsonGenerator jgen, SerializerProvider provider,
					PropertyWriter writer) throws Exception {
				// 只序列化基础类型
				if (writer.getType() instanceof SimpleType) {
					super.serializeAsField(pojo, jgen, provider, writer);
				}
			}
		});
		// 将过滤器应用到所有对象上
		dataMapper.addMixIn(Object.class, DataFilter.class);
		// 绑定过滤器
		dataMapper.writer(filterProvider);
		
	}

	public static String dataEncode(Object value) {
		try {
			return dataMapper.writeValueAsString(value);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static <T> T dataDecode(String content, TypeReference valueTypeRef) {
		try {
			return (T) dataMapper.readValue(content, valueTypeRef);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static <T> T dataDecode(String content, Class<T> valueType) {
		try {
			return (T) dataMapper.readValue(content, valueType);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static final ObjectMapper formatMapper;  
	static {
		formatMapper = new ObjectMapper();
		//包含空属性
		formatMapper.setSerializationInclusion(Include.ALWAYS);
		//美化输出
		formatMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
	}
	
	public static String formatEncode(Object value) {
		try {
			return formatMapper.writeValueAsString(value);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static <T> T formatDecode(String content, TypeReference valueTypeRef) {
		try {
			return (T) formatMapper.readValue(content, valueTypeRef);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static final ObjectMapper normarMapper;;
	
	static {
		normarMapper = new ObjectMapper();
		//包含空属性
		normarMapper.setSerializationInclusion(Include.ALWAYS);
		//美化输出
		normarMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		//序列化空对象不报错
		normarMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		//反序列化遇到未知属性不报错
		normarMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
	
	public static String encode(Object value) {
		try {
			return normarMapper.writeValueAsString(value);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static <T> T decode(String content, TypeReference valueTypeRef) {
		try {
			return (T) normarMapper.readValue(content, valueTypeRef);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static <T> T decode(String content, Class<T> valueType) {
		try {
			return (T) normarMapper.readValue(content, valueType);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}

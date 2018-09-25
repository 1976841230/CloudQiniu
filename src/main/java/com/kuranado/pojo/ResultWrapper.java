package com.kuranado.pojo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;

import java.util.List;


/**
 * @Author: Xinling Jing
 * @Date: 2018/9/11 0011 10:39
 */
@Data
public class ResultWrapper {
	// 定义jackson对象
	private static final ObjectMapper MAPPER = new ObjectMapper();

	// 响应业务状态
	private Integer status;

	// 响应消息
	private String msg;

	// 响应中的数据
	private Object data;

	public static ResultWrapper build(Integer status, String msg, Object data) {
		return new ResultWrapper(status, msg, data);
	}

	public static ResultWrapper ok(Object data) {
		return new ResultWrapper(data);
	}

	public static ResultWrapper ok() {
		return new ResultWrapper(null);
	}

	public ResultWrapper() {

	}

	public static ResultWrapper build(Integer status, String msg) {
		return new ResultWrapper(status, msg, null);
	}

	public ResultWrapper(Integer status, String msg, Object data) {
		this.status = status;
		this.msg = msg;
		this.data = data;
	}

	public ResultWrapper(Object data) {
		this.status = 200;
		this.msg = "OK";
		this.data = data;
	}

	/**
	 * 将json结果集转化为ResultWrapper对象
	 *
	 * @param jsonData json数据
	 * @param clazz ResultWrapper中的object类型
	 * @return
	 */
	public static ResultWrapper formatToPojo(String jsonData, Class<?> clazz) {
		try {
			if (clazz == null) {
				return MAPPER.readValue(jsonData, ResultWrapper.class);
			}
			JsonNode jsonNode = MAPPER.readTree(jsonData);
			JsonNode data = jsonNode.get("data");
			Object obj = null;
			if (clazz != null) {
				if (data.isObject()) {
					obj = MAPPER.readValue(data.traverse(), clazz);
				} else if (data.isTextual()) {
					obj = MAPPER.readValue(data.asText(), clazz);
				}
			}
			return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 没有object对象的转化
	 *
	 * @param json
	 * @return
	 */
	public static ResultWrapper format(String json) {
		try {
			return MAPPER.readValue(json, ResultWrapper.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Object是集合转化
	 *
	 * @param jsonData json数据
	 * @param clazz 集合中的类型
	 * @return
	 */
	public static ResultWrapper formatToList(String jsonData, Class<?> clazz) {
		try {
			JsonNode jsonNode = MAPPER.readTree(jsonData);
			JsonNode data = jsonNode.get("data");
			Object obj = null;
			if (data.isArray() && data.size() > 0) {
				obj = MAPPER.readValue(data.traverse(),
						MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
			}
			return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
		} catch (Exception e) {
			return null;
		}
	}


}

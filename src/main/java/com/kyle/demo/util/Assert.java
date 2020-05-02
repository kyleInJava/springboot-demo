package com.kyle.demo.util;

import java.util.Collection;
import java.util.Map;

import com.kyle.demo.common.ErrorInfo;
import com.kyle.demo.common.ServiceException;

/**
 * 用于校验数据，这样不用在业务代码使用throw Exception
 */
public class Assert {
	
	//不为空就抛出异常
	public static void isNull(Object obj, ErrorInfo info) {
		if(obj != null) {
			throw new ServiceException(info);
		}
	}
	
	//为空就抛出异常
	public static void notNull(Object obj, ErrorInfo info) {
		if(obj == null) {
			throw new ServiceException(info);
		}
	}
	
	//集合为null或没有元素就抛出异常
	public static void notEmpty(Collection<Object> collection,ErrorInfo info) {
		if(collection == null || collection.isEmpty()) {
			throw new ServiceException(info);
		}
	}
	
	//Map集合为null或没有元素就抛出异常
	public static void notEmpty(Map<Object,Object> map,ErrorInfo info) {
		if(map == null || map.isEmpty()) {
			throw new ServiceException(info);
		}
	}
	
	//String为null或者为""就抛出异常
	public static void notEmpty(String str,ErrorInfo info) {
		if(str == null || str.isEmpty()) {
			throw new ServiceException(info);
		}
	}
	
}

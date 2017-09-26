package com.springboot.sms.common;

public class StringUtils {
	
	public static boolean isEmpty(String str) {
		return (null == str) || (str.trim().length() == 0);
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}
	
	public static boolean isEmpty4Obj(Object obj) {
		return (null == obj) || ("".equals(obj));
	}

	public static boolean isNotEmpty4Obj(Object obj) {
		return !isEmpty4Obj(obj);
	}
}

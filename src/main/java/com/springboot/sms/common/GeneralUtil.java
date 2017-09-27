package com.springboot.sms.common;

import com.springboot.sms.common.EnumManage.SendStatusEnum;

/**
 * 公共工具类
 * @author zhushiwu
 */
public class GeneralUtil {

	public static BaseAnswer setBaseAnswer(SendStatusEnum state, String msg,Class<?> channel) {
		BaseAnswer answer = new BaseAnswer();
		answer.setStateCode(state.getStateCode());
		answer.setMessage(msg);
		answer.setClassName(channel.getSimpleName());
		return answer;
	}
	
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

package com.springboot.sms.common;

import com.springboot.sms.common.EnumManage.SendStatusEnum;

/**
 * 公共工具类
 * @author zhushiwu
 *
 */
public class GeneralUtil {

	public static BaseAnswer setBaseAnswer(BaseAnswer answer, SendStatusEnum state, String msg) {
		answer.setStatus(state, msg);
		return answer;
	}
}

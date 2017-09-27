package com.springboot.sms.channel.imp;

import java.net.URLEncoder;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.springboot.sms.channel.IChannelManage;
import com.springboot.sms.common.BaseAnswer;
import com.springboot.sms.common.BusinessException;
import com.springboot.sms.common.EnumManage.SendStatusEnum;
import com.springboot.sms.common.GeneralUtil;
import com.springboot.sms.config.ChannelConfigs.ChannelInfo;
import com.springboot.sms.http.HttpRequest;
import com.springboot.sms.mode.SmsSendData;


@Service("WangJianChannel")
public class WangJianChannel implements IChannelManage {
	@Resource
	private HttpRequest httpRequest;
	
	@Override
	public BaseAnswer sendSms(ChannelInfo channel, SmsSendData smsInfo) throws BusinessException {
		// 编码类型：0：GBK(默认) 1：UTF-8
		final String EN_CODE = "UTF-8";
		try {
			StringBuffer params = new StringBuffer();
			// 表单参数与get形式一样
			params.append("Uid=").append(channel.getUserName())
			.append("&Key=").append(channel.getPassword())
			.append("&smsMob=").append(smsInfo.getMobile())
			.append("&smsText=").append(URLEncoder.encode(smsInfo.getContent(), EN_CODE));
			
			String url = channel.getSendUrl() == null ? "" : channel.getSendUrl().toString();
			String result = httpRequest.doPost4Wj(url, params);
			return doResult(result);
		} catch (Exception e) {
			throw new BusinessException(SendStatusEnum.FAILURE, e);
		}
	}

	private BaseAnswer doResult(String result) {
		ErrorCode errorCode = ErrorCode.getErrorCode(result);
		if (errorCode == ErrorCode.SUCC) {
			return GeneralUtil.setBaseAnswer(SendStatusEnum.SUCCESS, "成功",this.getClass());
		} else {
			return GeneralUtil.setBaseAnswer(SendStatusEnum.FAILURE, errorCode.desc,this.getClass());
		}
	}
	private enum ErrorCode {
		SUCC("0", "正常发送"), 
		ERROR_CODE1("-1", "没有该用户账户"),
		ERROR_CODE2("-2", "接口密钥不正确"), 
		ERROR_CODE3("-3", "短信数量不足"),
		ERROR_CODE5("-4", "手机号格式不正确"),
		ERROR_CODE6("-6", "IP限制"),
		ERROR_CODE11("-11", "该用户被禁用"), 
		ERROR_CODE14("-14","短信内容出现非法字符"), 
		ERROR_CODE21("-21","MD5接口密钥加密不正确"), 
		ERROR_CODE41("-41", " 手机号码为空"), 
		ERROR_CODE42("-42", "短信内容为空"),
		ERROR_CODE51("-51", "短信签名格式不正确"),
		UNKNOWN("-100","未知错误"),
		;
		private String code;
		private String desc;

		ErrorCode(String code, String desc) {
			this.code = code;
			this.desc = desc;
		}

		private static ErrorCode getErrorCode(String code) {
			if (!GeneralUtil.isEmpty(code)) {
				ErrorCode[] itms = values();
				for (ErrorCode itm : itms) {
					if (code.trim().equals(itm.code)) {
						return itm;
					}
				}
			}
			return UNKNOWN;
		}
	}
}

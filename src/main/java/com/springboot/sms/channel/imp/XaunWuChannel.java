package com.springboot.sms.channel.imp;

import java.net.URLEncoder;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import com.springboot.sms.channel.IChannelManage;
import com.springboot.sms.common.BaseAnswer;
import com.springboot.sms.common.BusinessException;
import com.springboot.sms.common.EnumManage.SendStatusEnum;
import com.springboot.sms.common.GeneralUtil;
import com.springboot.sms.config.ChannelConfigs.ChannelInfo;
import com.springboot.sms.http.HttpRequest;
import com.springboot.sms.mode.SmsSendData;

@Service("XaunWuChannel")
public class XaunWuChannel implements IChannelManage{
	@Resource
	private HttpRequest httpRequest;
	
	@Override
	public BaseAnswer sendSms(ChannelInfo channel, SmsSendData smsInfo)  throws BusinessException  {
		//短信类型 1：普通短信 4：长短信
		final  int MSG_TYPE = 1;
		//编码类型：0：GBK(默认) 1：UTF-8
		final  int EN_CODE = 1;
		//版本号：1.0（1.0密码加密传输、为空密码以明文传输）
		final  String VERSION = "1.0";
		try {
			StringBuffer sb = new StringBuffer(channel.getSendUrl()==null?"":channel.getSendUrl().toString());
        	sb.append("?username=").append(channel.getUserName());
        	sb.append("&password=").append(DigestUtils.md5Hex(channel.getPassword()==null?"":channel.getPassword().toString()));
        	sb.append("&to=").append(smsInfo.getMobile());
        	//特殊字符编码处理
        	sb.append("&text=").append(URLEncoder.encode(smsInfo.getContent(),"UTF-8"));
        	sb.append("&subid=").append("&biztype=");
        	sb.append("&msgtype=").append(MSG_TYPE);
        	sb.append("&encode=").append(EN_CODE);
        	sb.append("&version=").append(VERSION);
        	sb.append("&batchid=").append(UUID.randomUUID());
        	System.out.println(sb.toString());
        	String result = httpRequest.doGet(sb.toString());
			return doResult(result);
		} catch (Exception e) {
			throw new BusinessException(SendStatusEnum.FAILURE,e);
		}
	}
	
	private BaseAnswer doResult(String result) {
		ErrorCode errorCode = ErrorCode.getErrorCode(result);
		if(errorCode==ErrorCode.SUCC){
			return GeneralUtil.setBaseAnswer(SendStatusEnum.SUCCESS,"成功",this.getClass());
		}else{
			return GeneralUtil.setBaseAnswer(SendStatusEnum.FAILURE,errorCode.desc,this.getClass());
		}
	}

	private enum ErrorCode {
		SUCC("0","正常发送"),
		ERROR_CODE2("-2","发送参数填写不正确"),
		ERROR_CODE3("-3","用户载入延迟"),
		ERROR_CODE6("-6","密码错误"),
		ERROR_CODE7("-7","用户不存在"),
		ERROR_CODE11("-11","发送号码数理大于最大发送数量"),
		ERROR_CODE12("-12","余额不足"),
		ERROR_CODE99("-99","内部处理错误"), 
		UNKNOWN("-100","未知错误");

		private String code;
		private String desc;
		ErrorCode(String code,String desc) {
			this.code = code;
			this.desc = desc;
		}
		private static ErrorCode getErrorCode(String code)
		{
			if(!GeneralUtil.isEmpty(code)){
				ErrorCode[] itms = values();
				for (ErrorCode itm : itms) {
					if(code.trim().equals(itm.code)){
						return itm;
					}
				}
			}
			return UNKNOWN;
		}
	}
	
}

package com.springboot.sms.channel.imp;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.springboot.sms.channel.IChannelManage;
import com.springboot.sms.common.BaseAnswer;
import com.springboot.sms.common.BusinessException;
import com.springboot.sms.common.EnumManage.SendStatusEnum;
import com.springboot.sms.common.GeneralUtil;
import com.springboot.sms.common.StringUtils;
import com.springboot.sms.config.ChannelConfigs.ChannelInfo;
import com.springboot.sms.http.HttpAPIService;
import com.springboot.sms.mode.SmsSendData;
@Service
public class XaunWuChannel implements IChannelManage{
	private final Gson gson = new GsonBuilder().create();
	@Resource
	private HttpAPIService httpAPIService;
	
	@Override
	public BaseAnswer sendSms(ChannelInfo channel, SmsSendData smsInfo)  throws BusinessException  {
		//短信类型 1：普通短信 4：长短信
		final  int MSG_TYPE = 1;
		//编码类型：0：GBK(默认) 1：UTF-8
		final  int EN_CODE = 1;
		//版本号：1.0（1.0密码加密传输、为空密码以明文传输）
		final  String VERSION = "1.0";
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("username", channel.getUserName());
		map.put("password", DigestUtils.md5Hex(channel.getPassword()==null?"":channel.getPassword().toString()));
		map.put("to", smsInfo.getMobile());
		map.put("text", smsInfo.getContent());
		map.put("msgtype", MSG_TYPE);
		map.put("encode", EN_CODE);
		map.put("version", VERSION);
		map.put("subID", "");
		map.put("batchid", UUID.randomUUID());
		System.out.println("sms info:" + gson.toJson(map));
		String url = channel.getSendUrl()==null?"":channel.getSendUrl().toString();
		try {
			String result = httpAPIService.doGet(url,map);
			System.out.println("----------------------------------------------------");
			System.out.println(result);
			System.out.println("----------------------------------------------------");
			return doResult(result);
		} catch (Exception e) {
			throw new BusinessException(SendStatusEnum.FAILURE,e);
		}
	}
	
	private BaseAnswer doResult(String result) {
		ErrorCode errorCode = ErrorCode.getErrorCode(result);
		if(errorCode==ErrorCode.SUCC){
			return GeneralUtil.setBaseAnswer(new BaseAnswer(),SendStatusEnum.SUCCESS,"成功");
		}else{
			return GeneralUtil.setBaseAnswer(new BaseAnswer(),SendStatusEnum.FAILURE,errorCode.desc);
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
			if(!StringUtils.isEmpty(code)){
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

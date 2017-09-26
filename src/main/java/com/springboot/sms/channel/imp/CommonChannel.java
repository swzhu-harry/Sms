package com.springboot.sms.channel.imp;

import org.springframework.stereotype.Service;

import com.springboot.sms.channel.IChannelManage;
import com.springboot.sms.common.BaseAnswer;
import com.springboot.sms.common.BusinessException;
import com.springboot.sms.config.ChannelConfigs.ChannelInfo;
import com.springboot.sms.mode.SmsSendData;

@Service
public class CommonChannel implements IChannelManage{

	@Override
	public BaseAnswer sendSms(ChannelInfo channel, SmsSendData smsInfo)  throws BusinessException {
		return null;
	}
	
}

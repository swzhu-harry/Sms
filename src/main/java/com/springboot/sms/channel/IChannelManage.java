package com.springboot.sms.channel;

import com.springboot.sms.common.BaseAnswer;
import com.springboot.sms.common.BusinessException;
import com.springboot.sms.config.ChannelConfigs.ChannelInfo;
import com.springboot.sms.mode.SmsSendData;

public interface IChannelManage {
	public BaseAnswer sendSms(ChannelInfo channel, SmsSendData smsInfo)  throws BusinessException ;
}

package com.springboot.sms.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.sms.channel.IChannelManage;
import com.springboot.sms.common.BaseAnswer;
import com.springboot.sms.common.BusinessException;
import com.springboot.sms.common.EnumManage.SendStatusEnum;
import com.springboot.sms.common.GeneralUtil;
import com.springboot.sms.common.SpringUtils;
import com.springboot.sms.config.ChannelConfigs;
import com.springboot.sms.config.ChannelConfigs.ChannelInfo;
import com.springboot.sms.mode.SmsSendData;
import com.springboot.sms.service.ISmsService;

@Service
public class SmsServiceImpl  implements ISmsService {
	@Autowired
	private ChannelConfigs channelConfigs;
	
	@Override
	public String sendSms(SmsSendData smsInfo)  {
		List<ChannelInfo> channels = channelConfigs.getChannelInfos();
		BaseAnswer answer = null;
		if(channels!=null && !channels.isEmpty()){
			for (ChannelInfo channel: channels) {
				answer = doSend(channel,smsInfo);
				if(SendStatusEnum.SUCCESS.getStateCode().equalsIgnoreCase(answer.getStateCode())){
					break;
				}
			}
		}
		return answer==null?SendStatusEnum.FAILURE.getStateCode():SendStatusEnum.SUCCESS.getStateCode();
	}

	private BaseAnswer doSend(ChannelInfo channel, SmsSendData smsInfo) {
		try {
			IChannelManage chm = (IChannelManage)SpringUtils.getBean(channel.getChannelName().serviceClass().getSimpleName());
			return chm.sendSms(channel, smsInfo);
		} catch (BusinessException e) {
			return GeneralUtil.setBaseAnswer(SendStatusEnum.FAILURE,e.getMessage(),this.getClass());
		}
	}
}

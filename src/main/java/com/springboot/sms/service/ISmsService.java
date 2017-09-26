package com.springboot.sms.service;

import com.springboot.sms.mode.SmsSendData;

public interface ISmsService {
	
	public String sendSms(final SmsSendData smsInfo);
}

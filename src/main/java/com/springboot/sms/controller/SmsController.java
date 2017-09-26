package com.springboot.sms.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.sms.mode.SmsSendData;
import com.springboot.sms.service.ISmsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author swzhu
 */
@RestController
@Api(tags={"Sms API"})
public class SmsController{
	
	@Autowired
	private ISmsService smsService;
	
	@RequestMapping(value = "/sms/send", method = RequestMethod.POST ) 
	@ApiOperation("发送短信")
	public String sendSms(@RequestBody SmsSendData req) {
		return smsService.sendSms(req);
	}
}

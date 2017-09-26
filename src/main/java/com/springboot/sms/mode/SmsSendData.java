package com.springboot.sms.mode;

import lombok.Data;

@Data
public class SmsSendData {
	//手机号码
	private String mobile;
	//内容
	private String content;
	//0-生产短信，1-营销短信，默认0
	private Integer smsType=0;
}

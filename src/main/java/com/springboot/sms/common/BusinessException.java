package com.springboot.sms.common;


import com.springboot.sms.common.EnumManage.SendStatusEnum;

import lombok.Getter;

public class BusinessException extends Exception {

	private static final long serialVersionUID = 9008391230839593438L;
	
	@Getter
	private SendStatusEnum statusEnum;
	
	public BusinessException() {
		super();
	}
	
	public BusinessException(String errorMessage){
		super(errorMessage);
	}
	
	public BusinessException(SendStatusEnum statusEnum){
		super(statusEnum.getStateCode());
		this.statusEnum = statusEnum;
	}
	public BusinessException(SendStatusEnum statusEnum, Throwable e){
		super(statusEnum.getStateCode(),e);
		this.statusEnum = statusEnum;
	}
}

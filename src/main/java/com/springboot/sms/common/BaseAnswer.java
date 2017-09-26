package com.springboot.sms.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springboot.sms.common.EnumManage.SendStatusEnum;

import lombok.Getter;

public class BaseAnswer{
	@JsonIgnore
	@Getter
	private SendStatusEnum status;
	@Getter
    private String stateCode;
	@Getter
    private String message;
	
	public BaseAnswer() {}
    public BaseAnswer(SendStatusEnum statusEnum,String msg) {
        setStatus(statusEnum,msg);
    }

    public void setStatus(SendStatusEnum statusEnum,String msg) {
    	this.status = statusEnum;
        this.stateCode = statusEnum.getStateCode();
        this.message = msg;
    }

	@Override
    public String toString() {
        return "BaseAnswer [stateCode=" + stateCode + ", message=" + message+"]";
    }

}

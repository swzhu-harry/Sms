package com.springboot.sms.common;

import lombok.Data;

@Data
public class BaseAnswer{
    private String stateCode;
    private String message;
    private String className;
}

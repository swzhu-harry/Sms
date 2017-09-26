package com.springboot.sms.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * @author zhushiwu
 */
@Data
@ConfigurationProperties(prefix="http")
@Configuration
public class HttpClientConfigs {	
    private Integer maxTotal = 100;
    private Integer defaultMaxPerRoute = 20;
    private Integer connectTimeout = 1000;
    private Integer connectionRequestTimeout = 500;
    private Integer socketTimeout = 10000;
    private boolean staleConnectionCheckEnabled = true;
}

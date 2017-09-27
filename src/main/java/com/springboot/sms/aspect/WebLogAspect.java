package com.springboot.sms.aspect;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.Data;


@Aspect
@Component
public class WebLogAspect  {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private final Gson gson = new GsonBuilder().create();
	
	@Pointcut("execution(public * com.springboot.sms.controller.*.*(..))")
	public void point() {}
	@Pointcut("execution(public * com.springboot.sms.channel.imp.*.sendSms(..))")
	public void channel() {}
	
	private ThreadLocal<String> threadId = new ThreadLocal<>();
	private ThreadLocal<Long> threadStartTime = new ThreadLocal<>();
	
	@Before(value="point()")
	public void logBefore(JoinPoint jPoint) {
		final ServletRequestAttributes requestAttr = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
		final HttpServletRequest request = requestAttr.getRequest();
		final String id = UUID.randomUUID().toString();
		threadId.set(id);
		
		// 记录下请求内容
		final LogBeforeInfo info = new LogBeforeInfo();
		info.id = id;
		info.uri = request.getRequestURI();
		info.method = request.getMethod();
		info.ip = request.getRemoteHost();
		info.params = jPoint.getArgs();
		info.startTime = System.currentTimeMillis();
		threadStartTime.set(info.startTime);
		
		log.info("请求参数日志:{} ",gson.toJson(info));
	}
	
	@AfterReturning(returning="result", pointcut="channel()")
	public void logResult(Object result) {
		final String id = threadId.get();
		
		final LogReturnInfo returnInfo = new LogReturnInfo();
		returnInfo.id = id;
		returnInfo.result = result;
		returnInfo.endTime = System.currentTimeMillis();
		
		final Long startTime = threadStartTime.get();
		if(startTime == null) {
			returnInfo.dealTime = -1;
		} else {
			returnInfo.dealTime = (int)(returnInfo.endTime - startTime);
		}
		
		log.info("请求结果记录:{} ",gson.toJson(returnInfo));
	}
	
	@AfterThrowing(throwing="error", pointcut="point()")
	public void logError(Throwable error) {
		final String id = threadId.get();
		
		final LogErrorInfo errorInfo = new LogErrorInfo();
		errorInfo.id = id;
		errorInfo.endTime = System.currentTimeMillis();
		
		final Long startTime = threadStartTime.get();
		if(startTime == null) {
			errorInfo.dealTime = -1;
		} else {
			errorInfo.dealTime = (int)(errorInfo.endTime - startTime);
		}
		
		log.error("请求异常记录:{} ",gson.toJson(errorInfo), error);
	}
	
	@Data
	private class LogBeforeInfo {
		private String id;
		private String uri;
		private String method;
		private String ip;
		private Object[] params;
		private long startTime;
	}
	
	@Data
	private class LogReturnInfo {
		private String id;
		private long endTime;
		private int dealTime;
		private Object result;
	}
	
	@Data
	private class LogErrorInfo {
		private String id;
		private long endTime;
		private int dealTime;
	}
}

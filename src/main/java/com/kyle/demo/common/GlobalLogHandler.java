package com.kyle.demo.common;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.kyle.demo.entity.RequestInfo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

//controller中日志信息
@Aspect
@Component
public class GlobalLogHandler {
	
	private Logger logger = LoggerFactory.getLogger("");
	
	@Pointcut("execution(public * com.kyle.demo.controller..*.*(..))")
    public void controllerAspect() {}
	
	@Around("controllerAspect()")
	public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable{
		long startTime = System.currentTimeMillis();
		RequestInfo ri = new RequestInfo();
		try {
			Object result = joinPoint.proceed();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			ri.setException(e);
			throw e;
		}finally {
			long endTime = System.currentTimeMillis();
			//如果想从request中获取数据，就这样获取request对象
			ServletRequestAttributes attributes =  (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
			HttpServletRequest request = attributes.getRequest();
			MethodSignature signature = (MethodSignature)joinPoint.getSignature();
			Object[] args = joinPoint.getArgs();
			String methodName = signature.getDeclaringTypeName()+"."+signature.getName();
			List<Object> params = Arrays.asList(args);
			ri.setIp(getRemoteHost(request));
			ri.setHttpMethod(request.getMethod());
			ri.setClassMethod(methodName);
			ri.setRequestParams(params);
			ri.setTimeCost(endTime-startTime);
			if(ri.getException() == null){
				logger.info(ri.toString());
			}else{
				logger.error(ri.toString());
			}
		}
		
		
	}

	//获取请求的ip地址
	private String getRemoteHost(HttpServletRequest request){
		String ip = request.getHeader("x-forwarded-for");
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
			ip = request.getHeader("Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
			ip = request.getRemoteAddr();
		}
		return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
	}

}

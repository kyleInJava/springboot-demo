package com.kyle.demo.common;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
	
	private Logger logger = LoggerFactory.getLogger(GlobalLogHandler.class);
	
	@Pointcut("execution(public * com.kyle.demo.controller..*.*(..))")
    public void controllerAspect() {}
	
	@Around("controllerAspect()")
	public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable{
		//如果想从request中获取数据，就这样获取request对象
		ServletRequestAttributes attributes =  (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
		
		long startTime = System.currentTimeMillis();
		long endTime = 0L;
		MethodSignature signature = (MethodSignature)joinPoint.getSignature();
		Object[] args = joinPoint.getArgs();
		String methodName = signature.getDeclaringTypeName()+"-"+signature.getName();
		List<Object> params = Arrays.asList(args);
		try {
			Object result = joinPoint.proceed();
			endTime = System.currentTimeMillis();
			logger.info("方法：{}",methodName);
			logger.info("参数：{}",params);
			logger.info("结果：{}",result);
			logger.info("耗时：{}",(endTime-startTime)+"ms");
			
			return result;
		} catch (Throwable e) {
			endTime = System.currentTimeMillis();
			StringWriter writer=new StringWriter();
		    e.printStackTrace(new PrintWriter(writer));
			logger.error("方法：{}",methodName);
			logger.error("参数：{}",params);
			logger.error("异常：{}",writer.toString());
			throw e;
		}
		
		
	} 

}

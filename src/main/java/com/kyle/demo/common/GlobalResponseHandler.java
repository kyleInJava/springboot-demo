package com.kyle.demo.common;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.alibaba.fastjson.JSON;


/**
 * 将所有的返回结果包装为Result，这样就能实现返回结果的格式统一
 * @author kyle
 *
 */
@RestControllerAdvice
public class GlobalResponseHandler implements ResponseBodyAdvice<Object>{

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
    	// 从异常对象中拿到ObjectError对象
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        // 然后提取错误提示信息进行返回
        return new Result(10000,objectError.getDefaultMessage());
    }
    
    @ExceptionHandler(ServiceException.class)
    public Result serviceExceptionHandler(ServiceException e) {
        return new Result(e);
    }
    
    @ExceptionHandler(Exception.class)
    public Result exceptionHandler(Exception e) {
    	return new Result(999,"系统发生未知错误："+e.getMessage());
    }

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		// 如果接口返回的类型本身就是Result那就没有必要进行额外的操作，返回false
		// 如果是要返回xml，也不用处理
		boolean flag = (!returnType.getGenericParameterType().equals(Result.class)) && (!converterType.equals(MappingJackson2XmlHttpMessageConverter.class));
        return flag;
	}

	//在返回结果之后，在结果被HttpMessageConverter转换之前执行
	@Override
	public Object beforeBodyWrite(Object data, MethodParameter returnType, MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
			ServerHttpResponse response) {
		//如果方法返回的类型是String类型，那么只能将返回结果放入Result对象中，然后转为json数据再返回
		if(returnType.getGenericParameterType() == String.class){
			return JSON.toJSONString(new Result(data));
		}
		return  new Result(data);
	}
    
}

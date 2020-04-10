package com.kyle.demo.common;

import lombok.Getter;

@Getter
public class Result {

	private int code;
	
	private String msg;
	
	private Object data;
	
	public Result(){
		this.code = ErrorInfo.SUCCESS.getCode();
		this.msg = ErrorInfo.SUCCESS.getMsg();
	}
	
	public Result(Object t){
		this();
		this.data = t;
	}
	
	public Result(ServiceException e){
		this.code = e.getCode();
		this.msg = e.getMsg();
	}
	
	public Result(int code ,String msg){
		this.code = code;
		this.msg = msg;
	}
}

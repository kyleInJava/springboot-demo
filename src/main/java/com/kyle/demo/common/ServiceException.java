package com.kyle.demo.common;

import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	protected int code;
	
	protected String msg;


	public ServiceException(String msg) {
		super(msg);
		this.msg = msg;
	}
	
	public ServiceException(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	public ServiceException(ErrorInfo errorInfo) {
		this.code = errorInfo.getCode();
		this.msg = errorInfo.getMsg();
	}

}

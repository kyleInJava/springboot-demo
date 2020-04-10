package com.kyle.demo.common;

import lombok.Getter;

@Getter
public enum ErrorInfo {
	
	SUCCESS(200,"成功"),
	ERROR(10001,"未知错误，请联系管理员")
	;
	
	ErrorInfo(int code, String msg){
		this.code = code;
		this.msg = msg;
	}
	
	
	private int code;
	
	private String msg;

}

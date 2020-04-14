package com.kyle.demo.entity;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Address {
	
	@NotNull(message = "地址id不能为空",groups = {DUR.class})
	private Long id;
	
	@Valid
	@NotNull(message = "地址不能为空")
	private String addr;

}

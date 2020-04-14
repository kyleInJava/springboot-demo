package com.kyle.demo.entity;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.kyle.demo.common.MyValidator;

import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "user")
public class User {
	
    @NotNull(message = "用户id不能为空",groups = {DUR.class})
    @JacksonXmlProperty(localName = "Id")
    private Long id;

    @NotNull(message = "用户账号不能为空")
    @Size(min = 6, max = 11, message = "账号长度必须是6-11个字符")
    private String account;

    @NotNull(message = "用户密码不能为空")
    @Size(min = 6, max = 11, message = "密码长度必须是6-16个字符")
    private String password;

    @NotNull(message = "用户邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;
    
    @MyValidator(message="手机格式不合法")
    private String phone;
    
    @Valid
    @NotNull(message = "地址不能为空")
    @Size(min = 1, message = "至少有一个地址")
    private List<Address> address;
}

package com.kyle.demo.service;

import java.util.Map;

import com.kyle.demo.common.PageBean;
import com.kyle.demo.entity.User;


public interface UserService {

	User getUser(User user);

	PageBean<User> list(Map<String,Object> param);

	boolean update(User user);

	boolean uupdate(User user);

}

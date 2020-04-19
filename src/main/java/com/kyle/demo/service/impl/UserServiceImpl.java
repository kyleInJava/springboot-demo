package com.kyle.demo.service.impl;

import java.util.List;
import java.util.Map;

import javax.jws.soap.SOAPBinding.Use;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.alibaba.fastjson.JSONObject;
import com.kyle.demo.common.PageBean;
import com.kyle.demo.dao.UserMapper;
import com.kyle.demo.entity.User;
import com.kyle.demo.service.UserService;
import com.kyle.demo.util.PageUtil;
import com.kyle.demo.util.RedisTool;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private PlatformTransactionManager txManager;
	
	@Autowired
    private RedisTool redisTool;

	@Override
	public User getUser(User user) {
		User object = (User)redisTool.get("user:"+user.getId());
		System.out.println(object);
		String jsonString = JSONObject.toJSONString(object);
		System.out.println(jsonString);
		if(object == null) {
			User u = userMapper.getUserById(user.getId());
			if(u != null) {
				redisTool.set("user:"+user.getId(),u);
			}
			return u;
		}
		return object;
	}

	@Override
	public PageBean<User> list(Map<String,Object> param) {
		PageUtil.startPage(param);
		List<User> users = userMapper.list();
		PageBean<User> result = new PageBean<>(users);
		return result;
	}

	//@Transactional
	@Override
	public boolean update(User user) {
		userMapper.update(user);
		user.setId(2L);
		user.setAccount("aaa");
		int i = 1/0;
		userMapper.update(user);
		return false;
	}
	
	//编程式事务
	@Override
	public boolean uupdate(User user) {
		TransactionStatus transaction = txManager.getTransaction(new DefaultTransactionDefinition());
		try{
			userMapper.update(user);
			user.setId(2L);
			user.setAccount("aaa");
			int i = 1/0;
			userMapper.update(user);
			txManager.commit(transaction);
		} catch(Exception e){
			txManager.rollback(transaction);
		}
		return false;
	}

}

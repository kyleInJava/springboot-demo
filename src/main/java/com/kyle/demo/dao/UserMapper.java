package com.kyle.demo.dao;

import java.util.List;

import com.kyle.demo.entity.User;

public interface UserMapper {
	
	User getUserById(Long id);

	List<User> list();

	int update(User user);

}

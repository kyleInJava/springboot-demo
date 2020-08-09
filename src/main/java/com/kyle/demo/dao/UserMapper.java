package com.kyle.demo.dao;

import com.kyle.demo.entity.User;

import java.util.List;

public interface UserMapper {
	
	User getUserById(Long id);

	List<User> list();

	int update(User user);

}

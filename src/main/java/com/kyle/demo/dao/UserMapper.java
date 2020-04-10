package com.kyle.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kyle.demo.entity.User;

@Mapper
public interface UserMapper {
	
	User getUserById(Long id);

	List<User> list();

	int update(User user);

}

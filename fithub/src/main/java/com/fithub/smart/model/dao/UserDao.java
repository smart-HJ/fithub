package com.fithub.smart.model.dao;

import java.util.List;

import com.fithub.smart.model.dto.User;

public interface UserDao {
	
	List<User> selectAll();
	
	User selectOne(String id);
	User selectOneByUserId(int userId);

	int insertUser(User user);

	User checkExist(String userInfo);

}

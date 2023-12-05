package com.fithub.smart.model.service;

import java.util.List;

import com.fithub.smart.model.dto.User;

public interface UserService {
	
	List<User> selectAll();

	User login(User user);

	int signUp(User user);
	
	User socialLogin(String userInfo);


}

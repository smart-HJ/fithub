package com.fithub.smart.model.service;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fithub.smart.model.dao.UserDao;
import com.fithub.smart.model.dto.User;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;

	@Override
	public List<User> selectAll() {
		return userDao.selectAll();
	}
	
	@Override
	public User login(User user) {
		User temp = userDao.selectOne(user.getId());
		if (temp != null && temp.getPassword().equals(user.getPassword()))
			return temp;
		return null;
	}

	@Override
	public int signUp(User user) {
		return userDao.insertUser(user);
	}

	@Override
	public User socialLogin(String userInfo) {
        User user = userDao.checkExist(userInfo);
        if (user != null) {
            return user;
        } else {// 회원가입
            User socialUser = new User();
            String id = RandomStringUtils.randomAlphanumeric(8) + "ID";
            while (userDao.selectOne(id) != null) {
                id = RandomStringUtils.randomAlphanumeric(8) + "ID";
            }
            String name = RandomStringUtils.randomAlphanumeric(8) + "NAME";
            String password = RandomStringUtils.randomAlphanumeric(8) + "PW";
            socialUser.setEmail(userInfo);
            socialUser.setId(id);
            socialUser.setName(name);
            socialUser.setPassword(password);
            userDao.insertUser(socialUser);
            return socialUser;
        }

    }


	
}

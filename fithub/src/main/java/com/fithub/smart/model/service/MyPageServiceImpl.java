package com.fithub.smart.model.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.fithub.smart.model.dao.MyPageDao;
import com.fithub.smart.model.dao.UserDao;
import com.fithub.smart.model.dto.Board;
import com.fithub.smart.model.dto.Lesson;
import com.fithub.smart.model.dto.SelectLessonDto;
import com.fithub.smart.model.dto.User;

@Service
public class MyPageServiceImpl implements MyPageService {

	@Autowired
	private MyPageDao myPageDao;
	@Autowired
	private UserDao userDao;

	@Override
	public List<Lesson> getMyLessons(int userId) {
		return myPageDao.getMyLessons(userId);
	}

	@Override
	public List<Lesson> oneDayLessons(SelectLessonDto dto) {
		return myPageDao.oneDayLessons(dto);
	}

	@Override
	public List<Board> getMyBoards(int userId) {
		return myPageDao.getMyBoards(userId);
	}

	@Override
	public User getUser(int userId) {
		return userDao.selectOneByUserId(userId);
	}

	private static String uploadPath = "src/main/resources/static/image/profile";

	@Override
	public int updateUser(MultipartFile file, int userId, String id) {
		User user = userDao.selectOneByUserId(userId);
		if (file != null) {
			DateTimeFormatter simpleDateFormat = DateTimeFormatter.ofPattern("yyMMddhhmmss");
			LocalDateTime now = LocalDateTime.now();
			String currentTime = now.format(simpleDateFormat);

			File folder = new File(uploadPath);
			if (!folder.exists())
				folder.mkdir();

			String fileName = currentTime + file.getOriginalFilename();
			File target = new File(uploadPath, fileName);

			try {
				FileCopyUtils.copy(file.getBytes(), target);
			} catch (IOException e) {
				e.printStackTrace();
			}
			user.setImg(fileName);
		}
		user.setId(id);
		return myPageDao.updateUser(user);
	}

	@Override
	public int changePassword(User user) {
		return myPageDao.changePassword(user);
	}

}

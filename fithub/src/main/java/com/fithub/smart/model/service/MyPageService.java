package com.fithub.smart.model.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fithub.smart.model.dto.Board;
import com.fithub.smart.model.dto.Lesson;
import com.fithub.smart.model.dto.SelectLessonDto;
import com.fithub.smart.model.dto.User;

public interface MyPageService {

	List<Lesson> getMyLessons(int userId);

	List<Lesson> oneDayLessons(SelectLessonDto dto);

	List<Board> getMyBoards(int userId);

	User getUser(int userId);

	int updateUser(MultipartFile file, int userId, String id);

	int changePassword(User user);

}

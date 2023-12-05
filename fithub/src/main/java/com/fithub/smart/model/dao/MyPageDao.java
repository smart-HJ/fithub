package com.fithub.smart.model.dao;

import java.util.List;

import com.fithub.smart.model.dto.Board;
import com.fithub.smart.model.dto.Lesson;
import com.fithub.smart.model.dto.SelectLessonDto;
import com.fithub.smart.model.dto.User;

public interface MyPageDao {

	List<Lesson> getMyLessons(int userId);

	List<Lesson> oneDayLessons(SelectLessonDto dto);

	List<Board> getMyBoards(int userId);

	int updateUser(User user);

	int changePassword(User user);


}

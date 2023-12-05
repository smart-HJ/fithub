package com.fithub.smart.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fithub.smart.model.dao.UserDao;
import com.fithub.smart.model.dto.Board;
import com.fithub.smart.model.dto.Lesson;
import com.fithub.smart.model.dto.SelectLessonDto;
import com.fithub.smart.model.dto.User;
import com.fithub.smart.model.service.MyPageService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api-mypage")
public class MyPageController {
	@Autowired
	private MyPageService myPageService;

	@GetMapping("lessons")
	public ResponseEntity<?> getMyLessons(int userId) {
		List<Lesson> lessons = myPageService.getMyLessons(userId);
		if (lessons.size() == 0 || lessons == null)
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<List<Lesson>>(lessons, HttpStatus.OK);
	}

	@GetMapping("day")
	public ResponseEntity<?> oneDayLessons(@RequestParam String date1, @RequestParam int userId) {
		SelectLessonDto dto = new SelectLessonDto(userId, date1);
		System.out.println(date1);
		List<Lesson> lessons = myPageService.oneDayLessons(dto);
		if (lessons.size() == 0 || lessons == null)
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<List<Lesson>>(lessons, HttpStatus.OK);
	}

	@GetMapping("board")
	public ResponseEntity<?> getMyBoards(@RequestParam int userId) {
		List<Board> boards = myPageService.getMyBoards(userId);
		if (boards.size() == 0 || boards == null)
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<List<Board>>(boards, HttpStatus.OK);
	}

	@GetMapping("user")
	public ResponseEntity<?> getUser(@RequestParam int userId) {
		User user = myPageService.getUser(userId);
		if (user != null) {
			user.setPassword("");
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@PutMapping(value = "update", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<?> regist(@RequestParam(value = "file", required = false) MultipartFile file,
			@RequestParam("userId") int userId, @RequestParam("id") String id) {
		System.out.println(file);
		System.out.println(userId);
		System.out.println(id);

		int result = myPageService.updateUser(file, userId, id);
		if (result == 1)
			return new ResponseEntity<Integer>(result, HttpStatus.OK);
		else
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@PutMapping("changePassword")
	public ResponseEntity<?> changePassword(@RequestBody User user) {
		System.out.println(user + "asdfadfadfa");
		int result = myPageService.changePassword(user);
		if (result == 1)
			return new ResponseEntity<Integer>(result, HttpStatus.OK);
		else
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}

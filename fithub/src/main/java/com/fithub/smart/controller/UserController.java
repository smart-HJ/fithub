package com.fithub.smart.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fithub.smart.model.dto.User;
import com.fithub.smart.model.service.UserService;
import com.fithub.smart.util.JwtUtil;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api-user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private JwtUtil jwtUtil;

	@GetMapping("user")
	@ApiOperation(value = "등록된 모든 사용자 정보를 반환한다.", response = User.class)
	public ResponseEntity<?> selectAll() {
		try {
			List<User> users = userService.selectAll();
			if (users != null && users.size() > 0) {
				return new ResponseEntity<List<User>>(users, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@PostMapping("login")
	@ApiOperation(value = "입력한 user의 id와 password가 같으면 user을 반환한다.", response = User.class)
	public ResponseEntity<?> login(@RequestBody User user) {
		try {
			User temp = userService.login(user);
			if (temp == null)
				return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
			Map<String, Object> result = new HashMap<>();
			result.put("access-token", jwtUtil.createToken(temp));
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@GetMapping("logout")
	@ApiOperation(value = "session에 저장되어 있던 유저의 정보를 지운다.")
	public ResponseEntity<Void> logout() {
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	private static String uploadPath = "src/main/resources/static/image/profile";

	@PostMapping(value = "regist", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	@ApiOperation(value = "정보를 받아 user 객체를 저장한다.", response = Integer.class)
	public ResponseEntity<?> regist(@RequestParam(required = false, value = "file") MultipartFile file,
			@RequestParam("id") String id, @RequestParam("name") String name, @RequestParam("password") String password,
			@RequestParam("email") String email, @RequestParam("phoneNum") String phoneNum) {

		User user = new User();

		if (file != null) {

			DateTimeFormatter simpleDateFormat = DateTimeFormatter.ofPattern("yyMMddhhmmss");
			LocalDateTime now = LocalDateTime.now();
			String currentTime = now.format(simpleDateFormat);

			if (file.isEmpty()) {
				return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
			}
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
		user.setName(name);
		user.setPassword(password);
		user.setEmail(email);
		user.setPhoneNum(phoneNum);

		int result = userService.signUp(user);
		if (result == 1)
			return new ResponseEntity<Integer>(result, HttpStatus.OK);
		else
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Sorry: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}

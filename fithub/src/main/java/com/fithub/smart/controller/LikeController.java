package com.fithub.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fithub.smart.model.dto.Like;
import com.fithub.smart.model.service.LikeService;

@RestController
@RequestMapping("/api-like")
public class LikeController {
	@Autowired
	private LikeService ls;

	@GetMapping("regist")
	public ResponseEntity<?> registLike(@RequestParam int userId, @RequestParam int boardId) {
		Like like = new Like(boardId, userId);
		boolean result = ls.registLike(like);
		if(result) return new ResponseEntity<Boolean>(result, HttpStatus.OK);
			return new ResponseEntity<Boolean>(result, HttpStatus.BAD_REQUEST);
	}
	@DeleteMapping("delete")
	public ResponseEntity<?> deleteLike(@RequestParam int userId, @RequestParam int boardId) {
		Like like = new Like(boardId, userId);
		boolean result = ls.deleteLike(like);
		if(result) return new ResponseEntity<Boolean>(result, HttpStatus.OK);
		return new ResponseEntity<Boolean>(result, HttpStatus.BAD_REQUEST);
	}
	

}

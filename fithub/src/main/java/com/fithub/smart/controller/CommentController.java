package com.fithub.smart.controller;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fithub.smart.model.dto.Comment;
import com.fithub.smart.model.service.CommentService;

@RestController
@RequestMapping("/api-comment")
public class CommentController {
	
	@Autowired
	private CommentService cs;
	
	// 게시글별 댓글 세팅
	@GetMapping("comments/{boardId}")
	public ResponseEntity<?> commentList(@PathVariable int boardId) {
		List<Comment> list = cs.commentList(boardId);
		if(list == null || list.size() == 0)
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<List<Comment>>(list, HttpStatus.OK);
	}
	
	@PostMapping("regist")
	public ResponseEntity<?> regsitComment(@RequestBody Comment comment) {
		boolean result = cs.registComment(comment);
		if(result) return new ResponseEntity<Boolean>(result, HttpStatus.CREATED);
			return new ResponseEntity<Boolean>(result, HttpStatus.BAD_REQUEST);
	}
	
	// 게시글 삭제
	@DeleteMapping("delete")
	public ResponseEntity<?> deleteComment(@RequestParam int commentId, @RequestParam String cocomment, 
				@RequestParam int boardId, @RequestParam int commentGroup){
		boolean result = cs.deleteComment(commentId, cocomment, boardId, commentGroup);
		if(result) return new ResponseEntity<Boolean>(result, HttpStatus.OK);
			return new ResponseEntity<Boolean>(result, HttpStatus.BAD_REQUEST);
	}
	

}

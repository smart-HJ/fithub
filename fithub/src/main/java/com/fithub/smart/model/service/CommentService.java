package com.fithub.smart.model.service;

import java.util.List;

import com.fithub.smart.model.dto.Comment;

public interface CommentService {

	List<Comment> commentList(int boardId);

	boolean registComment(Comment comment);

	boolean deleteComment(int commentId, String cocomment, int boardId, int commentGroup);
	
}

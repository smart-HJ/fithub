package com.fithub.smart.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fithub.smart.model.dao.CommentDao;
import com.fithub.smart.model.dto.Comment;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentDao cdao;
	
	@Override
	public List<Comment> commentList(int boardId) {
		return cdao.commentList(boardId);
	}

	@Override
	public boolean registComment(Comment comment) {
		int result = cdao.registComment(comment); 
		if(result > 0)
			return true;
		return false;
	}

	@Override
	public boolean deleteComment(int commentId, String cocomment,int boardId, int commentGroup) {
		int result = cdao.deleteComment(commentId, cocomment, boardId, commentGroup); 
		if(result > 0)
			return true;
		return false;
	}

}

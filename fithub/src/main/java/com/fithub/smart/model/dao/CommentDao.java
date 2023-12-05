package com.fithub.smart.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fithub.smart.model.dto.Comment;

public interface CommentDao {

	List<Comment> commentList(int boardId);

	int registComment(Comment comment);

	int deleteComment(@Param("commentId") int commentId, @Param("cocomment")String cocomment, @Param("boardId")int boardId, @Param("commentGroup")int commentGroup);

}

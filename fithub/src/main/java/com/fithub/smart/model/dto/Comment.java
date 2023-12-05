package com.fithub.smart.model.dto;

public class Comment {
	private int commentId;
	private int boardId;
	private int userId;
	private String id;
	private String content;
	private String regDate;
	private int commentGroup;
	private String cocomment;
	private String img;
	
	public Comment() {
	}

	public Comment(int boardId, int userId, String content, int commentGroup, String cocomment) {
		super();
		this.boardId = boardId;
		this.userId = userId;
		this.content = content;
		this.commentGroup = commentGroup;
		this.cocomment = cocomment;
	}

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public int getCommentGroup() {
		return commentGroup;
	}

	public void setCommentGroup(int commentGroup) {
		this.commentGroup = commentGroup;
	}

	public String getCocomment() {
		return cocomment;
	}

	public void setCocomment(String cocomment) {
		this.cocomment = cocomment;
	}
	

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	@Override
	public String toString() {
		return "Comment [commentId=" + commentId + ", boardId=" + boardId + ", userId=" + userId + ", id=" + id
				+ ", content=" + content + ", regDate=" + regDate + ", commentGroup=" + commentGroup + ", cocomment="
				+ cocomment + ", img=" + img + "]";
	}


}

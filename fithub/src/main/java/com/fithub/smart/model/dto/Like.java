package com.fithub.smart.model.dto;

public class Like {
	private int boardId;
	private int userId;

	public Like() {
	}

	public Like(int boardId, int userId) {
		super();
		this.boardId = boardId;
		this.userId = userId;
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

}

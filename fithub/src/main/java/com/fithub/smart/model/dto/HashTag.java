package com.fithub.smart.model.dto;

public class HashTag {
	private String hashtag;
	private int boardId;
	public HashTag() {
		// TODO Auto-generated constructor stub
	}
	
	public HashTag(String hashtag, int boardId) {
		super();
		this.hashtag = hashtag;
		this.boardId = boardId;
	}

	public String getHashtag() {
		return hashtag;
	}
	public void setHashtag(String hashtag) {
		this.hashtag = hashtag;
	}
	public int getBoardId() {
		return boardId;
	}
	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}
	
}

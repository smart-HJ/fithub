package com.fithub.smart.model.dto;

import java.util.List;

public class Board {
	private int boardId;
	private int userId;
	private String id;
	private String content;
	private int likeCnt;
	private String regDate;
	private String img;
	private String profileImg;
	private List<String> hashTags;
	private int islike;
	private int commentCnt;

	public Board() {
	}

	public Board(int userId, String id, String content) {
		super();
		this.userId = userId;
		this.id = id;
		this.content = content;
	}
	
	public Board(int boardId, int likeCnt, String regDate, String img, String profileImg) {
		super();
		this.boardId = boardId;
		this.likeCnt = likeCnt;
		this.regDate = regDate;
		this.img = img;
		this.profileImg = profileImg;
	}
	
	
	public Board(int boardId, int userId, String id, String content, int likeCnt, String regDate, String img,
			String profileImg, List<String> hashTags, int islike, int commentCnt) {
		super();
		this.boardId = boardId;
		this.userId = userId;
		this.id = id;
		this.content = content;
		this.likeCnt = likeCnt;
		this.regDate = regDate;
		this.img = img;
		this.profileImg = profileImg;
		this.hashTags = hashTags;
		this.islike = islike;
		this.commentCnt = commentCnt;
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
	

	public int getLikeCnt() {
		return likeCnt;
	}

	public void setLikeCnt(int likeCnt) {
		this.likeCnt = likeCnt;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getProfileImg() {
		return profileImg;
	}

	public void setProfileImg(String profileImg) {
		this.profileImg = profileImg;
	}

	public List<String> getHashTags() {
		return hashTags;
	}

	public void setHashTags(List<String> hashTags) {
		this.hashTags = hashTags;
	}

	
	public int getIslike() {
		return islike;
	}

	public void setIslike(int islike) {
		this.islike = islike;
	}
	
	

	public int getCommentCnt() {
		return commentCnt;
	}

	public void setCommentCnt(int commentCnt) {
		this.commentCnt = commentCnt;
	}

	@Override
	public String toString() {
		return "Board [boardId=" + boardId + ", userId=" + userId + ", id=" + id + ", content=" + content + ", likeCnt="
				+ likeCnt + ", regDate=" + regDate + ", img=" + img + ", profileImg=" + profileImg + ", hashTags="
				+ hashTags + ", islike=" + islike + ", commentCnt=" + commentCnt + "]";
	}


}

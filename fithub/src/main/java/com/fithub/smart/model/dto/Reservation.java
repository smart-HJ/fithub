package com.fithub.smart.model.dto;

public class Reservation {
	private int lessonId;
	private int userId;
	
	public Reservation() {
	}

	public Reservation(int lessonId, int userId) {
		super();
		this.lessonId = lessonId;
		this.userId = userId;
	}

	public int getLessonId() {
		return lessonId;
	}

	public void setLessonId(int lessonId) {
		this.lessonId = lessonId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	


	@Override
	public String toString() {
		return "Reservation [lessonId=" + lessonId + ", userId=" + userId + "]";
	}

	
	
}

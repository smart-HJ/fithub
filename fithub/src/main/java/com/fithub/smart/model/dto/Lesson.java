package com.fithub.smart.model.dto;

public class Lesson {
	private int lessonId;
	private int placeId;
	private String date;
	private String time;
	private int capacity;	//예약 가능 인원
	private int resCnt; 	//현재 예약 인원
	private boolean resFlag;  //true 예약
	
	public Lesson() {
	}

	public Lesson(int lessonId, int placeId, String date, String time, int capacity) {
		super();
		this.lessonId = lessonId;
		this.placeId = placeId;
		this.date = date;
		this.time = time;
		this.capacity = capacity;
	}

	public Lesson(int lessonId, int placeId, String date, String time, int capacity, int resCnt, boolean resFlag) {
		super();
		this.lessonId = lessonId;
		this.placeId = placeId;
		this.date = date;
		this.time = time;
		this.capacity = capacity;
		this.resCnt = resCnt;
		this.resFlag = resFlag;
	}

	public boolean isResFlag() {
		return resFlag;
	}

	public void setResFlag(boolean resFlag) {
		this.resFlag = resFlag;
	}

	public int getResCnt() {
		return resCnt;
	}

	public void setResCnt(int resCnt) {
		this.resCnt = resCnt;
	}

	public int getLessonId() {
		return lessonId;
	}

	public void setLessonId(int lessonId) {
		this.lessonId = lessonId;
	}

	public int getPlaceId() {
		return placeId;
	}

	public void setPlaceId(int placeId) {
		this.placeId = placeId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	@Override
	public String toString() {
		return "Lesson [lessonId=" + lessonId + ", placeId=" + placeId + ", date=" + date + ", time=" + time
				+ ", capacity=" + capacity + ", resCnt=" + resCnt + ", resFlag=" + resFlag + "]";
	}
	
	
}

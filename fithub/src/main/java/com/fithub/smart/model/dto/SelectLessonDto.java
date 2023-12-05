package com.fithub.smart.model.dto;

public class SelectLessonDto {
	private int placeId;
	private int userId;
	private String placeName ;
	private String date1 ;
	
	public SelectLessonDto() {
	}
	
	
	
	public SelectLessonDto(int userId, String date1) {
		super();
		this.userId = userId;
		this.date1 = date1;
	}



	public SelectLessonDto(String placeName, String date1) {
		super();
		this.placeName = placeName;
		this.date1 = date1;
	}

	public SelectLessonDto(String placeName, String date1,int userId) {
		super();
		this.userId=userId;
		this.placeName = placeName;
		this.date1 = date1;
	}

	
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getPlaceId() {
		return placeId;
	}

	public void setPlaceId(int placeId) {
		this.placeId = placeId;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public String getDate1() {
		return date1;
	}

	public void setDate1(String date1) {
		this.date1 = date1;
	}

	@Override
	public String toString() {
		return "SelectLessonDto [placeId=" + placeId + ", userId=" + userId + ", placeName=" + placeName + ", date1="
				+ date1 + "]";
	}


	
	
	
}

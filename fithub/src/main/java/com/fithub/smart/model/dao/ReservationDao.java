package com.fithub.smart.model.dao;

import java.util.List;

import com.fithub.smart.model.dto.Lesson;
import com.fithub.smart.model.dto.Reservation;
import com.fithub.smart.model.dto.SelectLessonDto;

public interface ReservationDao {
	
	List<Lesson> lessonList(SelectLessonDto selectLessonDto);

	int isRes(Reservation reservation);

	int resCnt(int lessonId);

	int reservationPay(Reservation reservation);

	int deleteRes(Reservation reservation);
}

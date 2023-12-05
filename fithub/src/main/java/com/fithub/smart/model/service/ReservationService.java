package com.fithub.smart.model.service;

import java.util.List;

import com.fithub.smart.model.dto.Lesson;
import com.fithub.smart.model.dto.Reservation;
import com.fithub.smart.model.dto.SelectLessonDto;

public interface ReservationService {
	List<Lesson> lessonLIst(SelectLessonDto selectLessonDto);

	boolean reservationPay(Reservation reservation);

	boolean deleteRes(Reservation reservation);
}

package com.fithub.smart.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fithub.smart.model.dao.ReservationDao;
import com.fithub.smart.model.dto.Lesson;
import com.fithub.smart.model.dto.Reservation;
import com.fithub.smart.model.dto.SelectLessonDto;

@Service
public class ReservationServiceImpl implements ReservationService {
	
	@Autowired
	private ReservationDao dao;

	@Override
	public List<Lesson> lessonLIst(SelectLessonDto selectLessonDto) {
		List<Lesson> list= dao.lessonList(selectLessonDto);
		for(Lesson lesson:list) {
			Reservation reservation=new Reservation();
			reservation.setUserId(selectLessonDto.getUserId());
			reservation.setLessonId(lesson.getLessonId());
			int cnt=dao.isRes(reservation);
			if(cnt>0)lesson.setResFlag(true);	
			int resCnt=dao.resCnt(lesson.getLessonId());
			lesson.setResCnt(resCnt);
		}
		System.out.println(list);
		return list;
	}

	@Override
    public boolean deleteRes(Reservation reservation) {
        int result = dao.deleteRes(reservation);
        if (result > 0)
            return true;
        return false;
    }

	
	@Override
	public boolean reservationPay(Reservation reservation) {
		int result = dao.reservationPay(reservation);
		if (result > 0)
			return true;
		return false;
	}
	
}

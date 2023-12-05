package com.fithub.smart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fithub.smart.model.dto.Lesson;
import com.fithub.smart.model.dto.Reservation;
import com.fithub.smart.model.dto.SelectLessonDto;
import com.fithub.smart.model.service.ReservationService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api-reservation")
public class ReservationController {
	
	@Autowired
	private ReservationService rs;
	
	@GetMapping("lesson")
	public ResponseEntity<?> lessonList(SelectLessonDto selectLessonDto){
		System.out.println(selectLessonDto);
		List<Lesson> list = rs.lessonLIst(selectLessonDto);
		if(list == null || list.size() == 0)
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<List<Lesson>>(list, HttpStatus.OK);
	}
	
	@DeleteMapping("delete")
    public ResponseEntity<?> deleteRes(@RequestParam int userId,@RequestParam int lessonId) {
        Reservation reservation=new Reservation(lessonId, userId);
        boolean result = rs.deleteRes(reservation);
        if(result) return new ResponseEntity<Boolean>(result, HttpStatus.OK);
            return new ResponseEntity<Boolean>(result, HttpStatus.BAD_REQUEST);
    }
	
	@PostMapping("resPay")
	public ResponseEntity<?> reservationPay(@RequestBody Reservation reservation) {
		boolean result = rs.reservationPay(reservation);
		if(result) return new ResponseEntity<Boolean>(result, HttpStatus.OK);
			return new ResponseEntity<Boolean>(result, HttpStatus.BAD_REQUEST);
	}
}

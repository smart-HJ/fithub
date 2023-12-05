package com.fithub.smart.controller;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.service.DefaultMessageService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/sendMessage")
public class SendMessageController {

	@GetMapping("msg")
	public ResponseEntity<String> sendSMS(@RequestParam String phoneNumber) {
		System.out.println("여기는 와??????/" + phoneNumber);
		DefaultMessageService messageService = NurigoApp.INSTANCE.initialize("NCSVYKYPPACZYSME",
				"TJIYE8TQWO1ZW6MYDUO89LFKJGZKA97H", "https://api.coolsms.co.kr");

		// Message 패키지가 중복될 경우 net.nurigo.sdk.message.model.Message로 치환하여 주세요
		Message message = new Message();
		String ranSu = RandomStringUtils.randomNumeric(6);
		message.setFrom("01090443111");
		message.setTo(phoneNumber);
		message.setText("인증번호는 : " + ranSu);

		try {
			// send 메소드로 ArrayList<Message> 객체를 넣어도 동작합니다!
			messageService.send(message);
		} catch (NurigoMessageNotReceivedException exception) {
			// 발송에 실패한 메시지 목록을 확인할 수 있습니다!
			System.out.println(exception.getFailedMessageList());
			System.out.println(exception.getMessage());
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
		}
		return new ResponseEntity<String>(ranSu, HttpStatus.OK);
	}
}

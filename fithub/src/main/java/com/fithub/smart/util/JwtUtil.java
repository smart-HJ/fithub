package com.fithub.smart.util;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.fithub.smart.model.dto.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
	
	private static final String SALT = "SMARTHAJUNG";
	
	
	//토큰 생성 메서드
	//데이터가 하나만 인자로 들어온다라고 가정하고 / 테스트에 방해가 되는 유효기간 제거
	public String createToken(User user) throws UnsupportedEncodingException {
		return Jwts.builder()
				.setHeaderParam("alg", "HS256")
				.setHeaderParam("typ", "JWT") //헤더완료
				.claim("userId", user.getUserId())
				.claim("id", user.getId())
				.claim("name", user.getName())
				.setExpiration(new Date(System.currentTimeMillis()+ (1000 * 60 * 60)))
				.signWith(SignatureAlgorithm.HS256, SALT.getBytes("UTF-8")) //서명완료
				.compact();
	}
	
	//토큰 유효성 검사 메서드
	public void valid(String token) throws Exception {
		Jwts.parser().setSigningKey(SALT.getBytes("UTF-8")).parseClaimsJws(token);
	}
}

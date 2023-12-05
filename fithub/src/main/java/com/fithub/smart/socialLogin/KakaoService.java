package com.fithub.smart.socialLogin;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class KakaoService {

	private final String KAKAO_TOKEN_URL = "https://kauth.kakao.com/oauth/token";
	private final RestTemplate restTemplate;

	@Value("${spring.social.kakao.app-id}")
	private String kakaoAppId;

	@Value("${spring.social.kakao.app-secret}")
	private String kakaoAppSecret;

	@Value("${spring.social.kakao.redirect}")
	private String kakaoRedirect;

	public KakaoService(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

	public String getKakaoToken(String code) {
		// 파라미터 설정
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
		parameters.add("grant_type", "authorization_code");
		parameters.add("client_id", kakaoAppId);
		parameters.add("client_secret", kakaoAppSecret);
		parameters.add("redirect_uri", kakaoRedirect);
		parameters.add("code", code);

		// 헤더 설정
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		// HTTP 요청 생성
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(parameters, headers);

		// 카카오 토큰 요청 API 호출
		ResponseEntity<String> response = restTemplate.postForEntity(KAKAO_TOKEN_URL, request, String.class);

		// JSON 파싱을 위한 ObjectMapper 객체 생성
		ObjectMapper objectMapper = new ObjectMapper();

		String accessToken = "";
		try {
			// JSON 응답을 Map 형태로 변환
			Map<String, String> responseMap = objectMapper.readValue(response.getBody(),
					new TypeReference<Map<String, String>>() {
					});
			// Map에서 "access_token" 키값으로 저장된 액세스 토큰 추출
			accessToken = responseMap.get("access_token");
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		// 액세스 토큰 반환
		return accessToken;
	}

	private final String KAKAO_USERINFO_URL = "https://kapi.kakao.com/v2/user/me";

	public String getKakaoUserInfo(String accessToken) {
	    HttpHeaders headers = new HttpHeaders();
	    headers.set("Authorization", "Bearer " + accessToken);

	    HttpEntity<HttpHeaders> request = new HttpEntity<>(headers);
	    ResponseEntity<String> response = restTemplate.exchange(KAKAO_USERINFO_URL, HttpMethod.GET, request,
	            String.class);

	    // JSON 파싱을 위한 ObjectMapper 객체 생성
	    ObjectMapper objectMapper = new ObjectMapper();

	    String email = "";
	    try {
	        // JSON 응답을 Map 형태로 변환
	        Map<String, Object> responseMap = objectMapper.readValue(response.getBody(), new TypeReference<Map<String, Object>>() {});
	        // "kakao_account" 키값으로 저장된 Map 추출
	        Map<String, Object> kakaoAccountMap = (Map<String, Object>) responseMap.get("kakao_account");
	        // Map에서 "email" 키값으로 저장된 이메일 추출
	        email = (String) kakaoAccountMap.get("email");
	    } catch (JsonProcessingException e) {
	        e.printStackTrace();
	    }

	    // 이메일 반환
	    return email;
	}


}

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
public class NaverService {

	private final String NAVER_TOKEN_URL = "https://nid.naver.com/oauth2.0/token";
	private final RestTemplate restTemplate;

	@Value("${spring.social.naver.app-id}")
	private String naverAppId;

	@Value("${spring.social.naver.app-secret}")
	private String naverAppSecret;

	@Value("${spring.social.naver.redirect}")
	private String naverRedirect;

	public NaverService(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

	public String getNaverToken(String code) {
		// 파라미터 설정
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
		parameters.add("grant_type", "authorization_code");
		parameters.add("client_id", naverAppId);
		parameters.add("client_secret", naverAppSecret);
		parameters.add("redirect_uri", naverRedirect);
		parameters.add("code", code);

		// 헤더 설정
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		// HTTP 요청 생성
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(parameters, headers);

		// 카카오 토큰 요청 API 호출
		ResponseEntity<String> response = restTemplate.postForEntity(NAVER_TOKEN_URL, request, String.class);

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

	private final String NAVER_USERINFO_URL = "https://openapi.naver.com/v1/nid/me";

	public String getNaverUserInfo(String accessToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);

		HttpEntity<HttpHeaders> request = new HttpEntity<>(headers);
		ResponseEntity<String> response = restTemplate.exchange(NAVER_USERINFO_URL, HttpMethod.GET, request,
				String.class);

		// JSON 파싱을 위한 ObjectMapper 객체 생성
		ObjectMapper objectMapper = new ObjectMapper();

		String email = "";
		try {
			// JSON 응답을 Map 형태로 변환
			Map<String, Object> responseMap = objectMapper.readValue(response.getBody(),
					new TypeReference<Map<String, Object>>() {
					});
			System.out.println(responseMap);
			// "response" 키값으로 저장된 Map 추출
			Map<String, Object> naverResponse = (Map<String, Object>) responseMap.get("response");
			// Map에서 "email" 키값으로 저장된 이메일 추출
			System.out.println(naverResponse+"뭐가 들어있니????????????/");
			email = (String) naverResponse.get("email");
			System.out.println(email);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		// 이메일 반환
		return email;
	}

}

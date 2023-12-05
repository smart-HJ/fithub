package com.fithub.smart.socialLogin;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.fithub.smart.model.dto.User;
import com.fithub.smart.model.service.UserService;
import com.fithub.smart.model.service.UserServiceImpl;
import com.fithub.smart.util.JwtUtil;

@RestController
@RequestMapping("app/login")
public class KakaoController {

	@Autowired
	private KakaoService kakaoService;

	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private JwtUtil jwtUtil;

	@GetMapping("/kakao")
	public RedirectView kakaoLogin(String code, HttpServletResponse response) {
		// 서버에 저장해야 로그아웃 할 수 있다.
		String accessToken = kakaoService.getKakaoToken(code);
		String userInfo = kakaoService.getKakaoUserInfo(accessToken);

		// 액세스 토큰과 사용자 정보를 한번에 반환하고 싶다면, 아래와 같이 Map에 담아서 반환할 수 있습니다.
		Cookie logoutcookie = new Cookie("K_accessToken", accessToken);
		logoutcookie.setMaxAge(60 * 60 * 24); // 쿠키 유효기간 설정 (예: 24시간)
		logoutcookie.setHttpOnly(false);
		logoutcookie.setPath("/");
		response.addCookie(logoutcookie);
		User user = userService.socialLogin(userInfo);
		Cookie cookie;
		try {
			cookie = new Cookie("access-token", jwtUtil.createToken(user));
			// 쿠키 설정 (Optional)
			cookie.setMaxAge(60 * 60 * 24); // 쿠키 유효기간 설정 (예: 24시간)
			cookie.setHttpOnly(false);
			cookie.setPath("/");
			cookie.setSecure(true); // SameSite=None을 사용하려면 Secure 속성도 필요합니다.
			response.addHeader("Set-Cookie", cookie.toString() + "; SameSite=None"); // SameSite 설정
			// 쿠키 추가
			response.addCookie(cookie);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new RedirectView("http://localhost:5173");// user숨겨서 데리고 login창에 자동으로 ㅈ넣기
	}

	@GetMapping("/kakao/logout")
	public RedirectView kakaoLogout(String code, HttpServletResponse response) {
		return new RedirectView("http://localhost:5173");
	}

}

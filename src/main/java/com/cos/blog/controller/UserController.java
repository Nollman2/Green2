package com.cos.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.cos.blog.model.KakaoProfile;
import com.cos.blog.model.OAuthToken;
import com.cos.blog.model.Users;
import com.cos.blog.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller //jsp파일을 넘겨주므로 Controller를 사용 //데이터를 넘겨줄때는 RestController
public class UserController {
	
	@Autowired
	private UserService userService;	
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Value("${cos.key}")
	private String cosKey;
	
	//회원가입페이지
	@GetMapping("/auth/joinForm") //localhost:8008/auth/joinForm의 주소로 요청시
	public String joinForm() {		
		//해당경로를 읽어서 파일을 출력함
		// 1. "src/main/webapp" 을 읽음
		// 2. yml파일의 mvc패턴 "/WEB-INF/views/" 를 읽음 //기본적으로 jsp파일을 읽지 못하므로 mvc를통해 읽음
		// 3. return "user/joinForm" 을 읽음
		// 4. yml파일의 .jps를 읽음
		// src/main/webapp/WEB-INF/views/user/joinForm.jsp
		return "user/joinForm";		
	}
	
	//로그인페이지
	@GetMapping("/auth/loginForm")
	public String loginForm() {		
		return "user/loginForm";
	}	

	//회원수정페이지
	@GetMapping("/user/updateForm")
	public String updateForm() {		
		return "user/updateForm";
	}
	
	//아이디찾기페이지
	@GetMapping("/auth/findId")
	public String fidId() {
		return "user/findId";
	}	

	//비밀번호재발급페이지
	@GetMapping("/auth/findpwd")
	public String fidpwd() {
		return "user/findpwd";
	}
	
	//카카오로그인	
	@GetMapping("/auth/kakao/callback")
	public String kakaocallback(String code) {
		
		// 첫번째 과정 : 발급받은 인증번호로 엑세스토큰을 요청하는 과정
		// http요청을 편하게 할 수 있음
		// rt에 토큰요청에 필요한 data를 담아서 보냄
		RestTemplate rt = new RestTemplate();
		
		// 1.HttpHeader 오브젝트 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		// 2.HttpBody 오브젝트생성
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code"); //고정값
		params.add("client_id", "7b2e0649514d4ef6451afbb3084f61aa"); //클라이언트앱키
		params.add("redirect_uri", "http://localhost:8002/auth/kakao/callback"); //카카오콜백주소
		params.add("code", code); //발급받은 인증코드값
		
		// 3.HttpHeader와 HttpBody를 하나의 오브젝트에 담기 		
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = // kakaoTokenRequest이 headers, params값을 가지게됨
				new HttpEntity<>(params, headers);
		
		// 4.생성한 kakaoTokenRequest를 아래의 방식으로 요청	
		// 5.요청이 정상처리되면 response에 tokken이 담겨지게 됨
		ResponseEntity<String> response = rt.exchange(
				"https://kauth.kakao.com/oauth/token", //토큰요청주소 카카오페이지에서 있음
				HttpMethod.POST, // POST방식요청
				kakaoTokenRequest, // 토큰요청에 필요한 정보가 담긴 오브젝트
				String.class // String 타입으로 가져옴
		);			
		
		//ObjectMapper를 이용해 response의 정보로 토큰이 담긴 객체 생성
		//Gson,Json,Simple,ObjectMapper
		ObjectMapper objectMapper = new ObjectMapper();
		OAuthToken oauthToken = null;
		try {
			oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}		
		
		
		// 두번째과정 : 발급받은 엑세스토큰을 이용해 사용자 정보 조회
		RestTemplate rt2 = new RestTemplate();				
		
		//HttpHeader 오브젝트 생성
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization","Bearer "+oauthToken.getAccess_token());
		headers2.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");
		
		//HttpHeader와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 = 
				new HttpEntity<>(headers2);
		
		//Http 요청하기 - Post방식으로 -그리고 response 변수의 응답 받음.
		ResponseEntity<String> response2 = rt2.exchange(
				"https://kapi.kakao.com/v2/user/me",
				HttpMethod.POST,
				kakaoProfileRequest2,
				String.class
				);
		
		// 세번째과정 : 조회한 정보를 토대로 DB에 저장 혹은 조회 후 로그인 
		//response2에는 토큰을이용해 조회한 사용자의 정보가 들어있음
		//정보를 토대로 DB를 통해 회원가입 또는 로그인을 진행함
		ObjectMapper objectMapper2 = new ObjectMapper();
		KakaoProfile kakaoProfile = null;
		try {
			kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		Users kakaoUser = Users.builder()
				.username(kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId())
				.password(cosKey)
				.email(kakaoProfile.getKakao_account().getEmail())
				.birth("null")
				.tel("null")
				.username2(kakaoProfile.getProperties().getNickname())
				.oauth("kakao")
				.build();
		
		Users originUser = userService.회원찾기(kakaoUser.getUsername());
		
		System.out.println(originUser);
		
		 if(originUser.getUsername() == null) {
		 System.out.println("기존 회원이 아닙니다..........."); 
		 userService.회원가입(kakaoUser); 
		 }
		 
		 Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(),cosKey));
		 SecurityContextHolder.getContext().setAuthentication(authentication);
			
		return "redirect:/";	
	}
}

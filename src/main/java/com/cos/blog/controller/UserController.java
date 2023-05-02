package com.cos.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller //jsp파일을 넘겨주므로 Controller를 사용 //데이터를 넘겨줄때는 RestController
public class UserController {
	
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

}

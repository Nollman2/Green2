package com.cos.blog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.Users;
import com.cos.blog.service.UserService;

@RestController //데이터를 주고받으므로 RestController
public class UserApiController {

	@Autowired
	private UserService userservice;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	
	// 회원가입
	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody Users user) {
		
		userservice.회원가입(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
		// ResponseDto의 data는 1
		// ResponseDto의 state는 HttpStatus.OK.value()
		// 이 값을 요청한 user.js의 save:function()으로 돌려준다
	}
	
	// 회원정보수정
	@PutMapping("/user/update")
	public ResponseDto<Integer> update(@RequestBody Users user) {
				
		userservice.회원수정(user);
		
		//트랜잭션이 종료되기 때문에 DB값은 변경이 되었음
		//하지만 세션값은 변경되지 않은 상태이기 때문에 직접 세션값을 변경해 줘야함
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		SecurityContext securityContext = SecurityContextHolder.getContext();
		securityContext.setAuthentication(authentication);		
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
		
	}
}

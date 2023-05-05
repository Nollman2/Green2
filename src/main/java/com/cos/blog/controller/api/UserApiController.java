package com.cos.blog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.ResponseDto;
import com.cos.blog.dto.SendTmpPwdDto;
import com.cos.blog.model.Users;
import com.cos.blog.service.UserService;

@RestController //데이터를 주고받으므로 RestController
public class UserApiController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	
	// 회원가입
	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody Users user) {
		
		userService.회원가입(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
		// ResponseDto의 data는 1
		// ResponseDto의 state는 HttpStatus.OK.value()
		// 이 값을 요청한 user.js의 save:function()으로 돌려준다
	}
	
	// 아이디 중복 체크
	@PostMapping("/auth/user/check")
	public ResponseDto<Integer> findByUsername(@RequestBody String username) {
		int i = 0;
		//해당아이디 존재 시 return값이 null아 아니므로 중복된 아이디
		//i값은 해당아이디의 id값이 되므로 0이 아니게 된다
		if (userService.중복체크(username) != null) {
			i = userService.중복체크(username).getId();
		}
		return new ResponseDto<Integer>(HttpStatus.OK.value(), i);
	}
	
	// 회원정보수정
	@PutMapping("/user/update")
	public ResponseDto<Integer> update(@RequestBody Users user) {
				
		userService.회원수정(user);
		
		//트랜잭션이 종료되기 때문에 DB값은 변경이 되었음
		//하지만 세션값은 변경되지 않은 상태이기 때문에 직접 세션값을 변경해 줘야함
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		SecurityContext securityContext = SecurityContextHolder.getContext();
		securityContext.setAuthentication(authentication);		
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
		
	}
	
	// 회원 탈퇴
	@DeleteMapping("/user/out/{id}")
	public ResponseDto<Integer> deleteById(@PathVariable int id) {
		userService.회원탈퇴(id);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	// 아이디찾기
	@PostMapping("/auth/id/search")
	public ResponseDto<String> 아이디찾기(@RequestBody Users user) {		
		
		String i = null;
		userService.아이디찾기(user);				
		i = userService.아이디찾기(user).getUsername();
		
		return new ResponseDto<String>(HttpStatus.OK.value(), i);
	}
	
	// 비밀번호 재발급
	@PostMapping("/auth/findpwd")
	public ResponseDto<Integer> find(@RequestBody SendTmpPwdDto dto) {
		int i = 0;
		
		if(userService.비밀번호재발급(dto.getUsername(), dto.getEmail()) !=null) {			
			userService.sendTmpPwd(dto);			
			i=userService.비밀번호재발급(dto.getUsername(), dto.getEmail()).getId();
		}
		System.out.println(i);		
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), i);
	}

}

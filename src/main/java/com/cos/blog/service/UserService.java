package com.cos.blog.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cos.blog.dto.SendTmpPwdDto;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.Users;
import com.cos.blog.repository.CheckUserRepository;
import com.cos.blog.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {		

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CheckUserRepository checkUserRepository;

	@Autowired
	private BCryptPasswordEncoder encodeer;

	// 회원가입
	@Transactional
	public void 회원가입(Users user) {
		String rawPassword = user.getPassword();
		String encPassword = encodeer.encode(rawPassword);
		user.setPassword(encPassword);
		user.setRoles(RoleType.USER);
		userRepository.save(user);
	}
	
	// 아이디 중복체크
	@Transactional
	public Users 중복체크(String username) {
		Users check = checkUserRepository.findByUsername(username);
		return check;
	}
	
	// 회원정보 수정
	@Transactional
	public void 회원수정(Users user) {
		Users persistance = userRepository.findById(user.getId()).orElseThrow(() -> {
			return new IllegalArgumentException("회원 찾기 실패");
		});
		String rawPassword = user.getPassword();
		String encPassword = encodeer.encode(rawPassword);
		persistance.setPassword(encPassword);		
		persistance.setUsername2(user.getUsername2());
		persistance.setBirth(user.getBirth());
		persistance.setAddress(user.getAddress());
		persistance.setTel(user.getTel());
		persistance.setEmail(user.getEmail());
	}		
	
	// 회원 탈퇴
	@Transactional
	public void 회원탈퇴(int id) {
		userRepository.deleteById(id);
	}
	
	// 아이디찾기
	public Users 아이디찾기(Users user) {		
		
		String username2 = user.getUsername2();
		String email = user.getEmail();		

		return userRepository.findByUsername2AndEmail(username2, email).orElseThrow(() -> {
			return new IllegalArgumentException("아이디 찾기 실패");

		});
	}
	
	//비밀번호 재발급
	public Users 비밀번호재발급(String username, String email) {
		
		Users check2 = userRepository.findByUsernameAndEmail(username, email);
		
		return check2;
		
		/*

		return userRepository.findByUsernameAndEmail(username, email).orElseThrow(() -> {
			return new IllegalArgumentException("아이디 찾기 실패");

		});
		*/
	}
	
	// 메일보내기 
	@Value("${spring.mail.username}")
	private String sendFrom;	
	
	private final JavaMailSender javaMailSender;
	
	// 임시패스워드 발송
	@Transactional
	public void sendTmpPwd(SendTmpPwdDto dto) {

		char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
				'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

		String tmpPwd = "";

		// 문자 배열 길이의 값을 랜덤으로 10개를 뽑아 구문을 작성함
		int idx = 0;
		for (int i = 0; i < 10; i++) {
			idx = (int) (charSet.length * Math.random());
			tmpPwd += charSet[idx];
		}				
		
		//메일 보내기
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(dto.getEmail());
			message.setFrom(sendFrom);
			message.setSubject("임시 비밀번호 입니다.");
			message.setText("임시비밀번호 안내 관련 이메일 입니다.\n" + "임시 비밀번호를 발급하오니 로그인 하신 후\n"
					+ "반드시 비밀번호를 변경해주시기 바랍니다.\n\n" + "임시 비밀번호 : " + tmpPwd);
			javaMailSender.send(message);
			
		} catch (MailParseException e) {
			e.printStackTrace();
		} catch (MailAuthenticationException e) {
			e.printStackTrace();
		} catch (MailSendException e) {
			e.printStackTrace();
		} catch (MailException e) {
			e.printStackTrace();
		}
		
		
		Users user = userRepository.findByUsername(dto.getUsername()).orElseThrow(() -> {
			return new IllegalArgumentException("임시 비밀번호 변경 실패: 사용자 이름을 찾을 수 없습니다.");
		});

		user.setPassword(encodeer.encode(tmpPwd));
		System.out.println(tmpPwd);
		
	}
	
	@Transactional
	public Users 회원찾기(String username) {

		// orElseGet-회원을 찾았는데 없으면 빈 객체를 리턴
		Users user = userRepository.findByUsername(username).orElseGet(() -> {
			return new Users();
		});
		return user;

	}
	
	// 인증번호 발송
	@Transactional
	public String sendJoinNumber(String email) {

		char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

		String joinNumber = "";

		int idx = 0;
		for (int i = 0; i < 6; i++) {
			idx = (int) (charSet.length * Math.random());
			joinNumber += charSet[idx];
		}

		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(email);
			message.setFrom(sendFrom);
			message.setSubject("인증번호 입니다.");
			message.setText("안녕하세요.\n" + "인증번호 안내 관련 이메일 입니다.\n" + "인증번호 : " + joinNumber);
			javaMailSender.send(message);
		} catch (MailParseException e) {
			e.printStackTrace();
		} catch (MailAuthenticationException e) {
			e.printStackTrace();
		} catch (MailSendException e) {
			e.printStackTrace();
		} catch (MailException e) {
			e.printStackTrace();
		}

		System.out.println(joinNumber);

		return joinNumber;
	}


}

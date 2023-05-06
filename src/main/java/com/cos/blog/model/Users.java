package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name="users22")
@Data //Getter Setter생성
@NoArgsConstructor //빈생성자
@AllArgsConstructor //전체생성자
@Builder //빌더패턴
@Entity	//user 클래스가 자동으로 db에 테이블이 생성된다.
@SequenceGenerator(	//시퀀스생성
		name = "USER22_SEQ_GENERATOR"
		, sequenceName = "USER22_SEQ"
		, initialValue = 1
		, allocationSize = 1
		)

public class Users {
	@Id	//Primary Key
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "USER22_SEQ_GENERATOR")
	private int id;	//시퀀스
	
	@Column(nullable = false, length = 30, unique = true) //공백불가 중복불가
	private String username;	//아이디
	
	@Column(nullable = false, length = 100)	//해쉬로 변경하여 암호화 length 크기
	private String password;
	
	@Column(length = 30)
	private String username2;	//이름		
	
	@Column(nullable = true, length = 30)
	private String birth;	//생년월일
	
	@Column(nullable = true, length = 100)
	private String address;	//주소	
	
	@Column(nullable = true, length = 50)
	private String tel;	//전화번호			
	
	@Column(nullable = true, length = 30)
	private String email;	//이메일		
	
	private String oauth; //Kakao 회원판별
	
	//직접입력하지 않고 enum RoleType의 테이터를 이용
	//RoleType은 데이터베이스에서 인식할수 없음 EnumType.STRING으로 알려줌)
	@Enumerated(EnumType.STRING)
	private RoleType roles;	
	
	@CreationTimestamp	//시간이 자동으로 입력
	private Timestamp createDate;
}

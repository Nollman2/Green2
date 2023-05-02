package com.cos.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.blog.config.auth.PrincipalDetailService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private PrincipalDetailService principalDetailService;
	
	@Bean
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
	    return super.authenticationManagerBean();
	}
	
	//시큐리티로그인이 실행되는 매서드
	//시큐리티가 대신 로그인 하기위해 password를 가로채기함
	//해당password가 무엇으로 해쉬 되어 회원가입이 되었는지 알아야함 
	//같은 해쉬로 암호화 해서 DB에 있는 해쉬랑 비교할 수 있음
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()	//csrf 토큰 비활성화(테스트시 걸어두는게 좋음)
			.authorizeRequests()
			.antMatchers("/","/auth/**","/js/**","/css/**","/img/**","/shelter/**")
			.permitAll()
			.anyRequest()	//이게 아닌 다른 모든 요청은
			.authenticated()	//인증이 필요
		.and()
			.formLogin()
			.loginPage("/auth/loginForm")
			.loginProcessingUrl("/auth/loginProc")			
		//스프링 시큐리티가 해당 주소로 요청오는 로그인을 가로채서 대신 로그인한다
		//가로쳐서 로그인 할 때 그때 만들어야할 클래스가 있다.
		//userdetails 타입의 user오브젝트를 만들어줘야한다.
		//user 오브젝트 타입을 맞춰줘야 가로챌 수 있다
			.defaultSuccessUrl("/");
		//로그인 성공 후에 요청되는 페이지
		// .failureUrl("로그인 실패 후 요청 페이지")
	}	
	
	
}

package com.cos.blog.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.blog.model.Users;

import lombok.Getter;

@Getter
public class PrincipalDetail implements UserDetails{
	
	private Users user;
	
	public PrincipalDetail(Users user) {
		this.user = user;
	}	

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}
	
	//계정이 만료되지 않았는지 리턴(true:만료안됨)
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	//계정이 잠겨있지 않았는지 리턴(true:잠기지 않음)
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	//비밀번호가 만료되지 않았는지 리턴(true:만료안됨)
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	//계정활성화가능여부 리턴(true:활성화)
	@Override
	public boolean isEnabled() {
		return true;
	}

	
	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collectors = new ArrayList<>();
		collectors.add(()->{return "ROLE_"+user.getRoles();});
		return collectors;
	}
	
}

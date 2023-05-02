package com.cos.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.Users;


public interface CheckUserRepository extends JpaRepository<Users,Integer> {
	Users findByUsername(String username);

	
}

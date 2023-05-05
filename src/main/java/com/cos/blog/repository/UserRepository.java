package com.cos.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.Users;

public interface UserRepository extends JpaRepository<Users,Integer>{

	Optional<Users> findByUsername(String username);	

	Optional<Users> findByUsername2AndEmail(String username2, String email);

	Users findByUsernameAndEmail(String username, String email);
	
	
	
}

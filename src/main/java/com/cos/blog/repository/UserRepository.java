package com.cos.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.Users;

public interface UserRepository extends JpaRepository<Users,Integer>{

	Optional<Users> findByUsername(String username);
	
	
}

package com.example.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.auth.model.AppUser;


//user repository to manage user entity
public interface UserRepo extends JpaRepository<AppUser, Long> {
	
	AppUser findByEmail(String email);
	Boolean existsByEmail(String email);
}

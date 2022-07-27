package com.example.auth.repository;

import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.auth.model.AppUser;
import com.example.auth.model.SavedStation;

public interface BikeRepo extends JpaRepository<SavedStation, Long> {
	Set<SavedStation> findByUser(AppUser user);
	@Transactional
	void deleteById(int id);
}

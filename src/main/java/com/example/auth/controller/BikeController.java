package com.example.auth.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.auth.model.AppUser;
import com.example.auth.model.SavedStation;
import com.example.auth.repository.BikeRepo;
import com.example.auth.repository.UserRepo;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/bike")  //auth url
public class BikeController {
	
	@Autowired
	BikeRepo bikeRepository;
	@Autowired
	UserRepo userRepository;
	
	@GetMapping("/users/{email}/saves")
	  public ResponseEntity<Set<SavedStation>> getAllFavesByEmail(@PathVariable(value = "email") String email) {
	    if (!userRepository.existsByEmail(email)) {
	      throw new RuntimeException("Not found Email = " + email);
	    }
	    AppUser currentUser = userRepository.findByEmail(email);
	    Set<SavedStation> faves = bikeRepository.findByUser(currentUser);
	    return new ResponseEntity<>(faves, HttpStatus.OK);
	  }
	
	
	  @GetMapping("/saves/{id}")
	  public ResponseEntity<SavedStation> getFavesByEmail(@PathVariable(value = "id") Long id) {
		  SavedStation saved = bikeRepository.findById(id)
	        .orElseThrow(() -> new RuntimeException("Not found saved with id = " + id));
	    return new ResponseEntity<>(saved, HttpStatus.OK);
	  }
	  
	  
	  @PostMapping("/users/{email}/save")
	  public ResponseEntity<SavedStation> createSaves(@PathVariable(value = "email") String userEmail,
	      @RequestBody SavedStation savedRequest) throws RuntimeException {
		  AppUser currentUser = userRepository.findByEmail(userEmail);
		  savedRequest.setUser(currentUser);
		  SavedStation saved = bikeRepository.save(savedRequest);
		  return new ResponseEntity<>(saved, HttpStatus.CREATED);
	  }
	  
	  
	  @PutMapping("/saved/{id}")
	  public ResponseEntity<SavedStation> updateSaves(@PathVariable("id") long id, @RequestBody SavedStation savedRequest) {
	    
		  SavedStation saved = bikeRepository.findById(id)
	        .orElseThrow();
	    saved.setBikeNumber(savedRequest.getBikeNumber());
	    return new ResponseEntity<>(bikeRepository.save(saved), HttpStatus.OK);
	  }
	  
	  @DeleteMapping("/{email}/saved/{id}")
	  public ResponseEntity<HttpStatus> deleteSaved(@PathVariable("email")String email,@PathVariable("id") long id) throws RuntimeException {
		  Set <SavedStation> faves = bikeRepository.findByUser(userRepository.findByEmail(email));
		  Boolean stationExists = false;
		  int bikeId = 0;
		  for (SavedStation s : faves) {
			  if (s.getBikeNumber() == id) {
				  stationExists = true;
				  bikeId = s.getId();
			  }
		  }
		  if (stationExists) {
			  bikeRepository.deleteById(bikeId);
		  }
		  else {
			  throw new RuntimeException("Not found Email = " + email);
		  }
		  
	    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	  }
	  
//	  @DeleteMapping("/users/{email}/saved")
//	  public ResponseEntity<List<SavedStation>> deleteAllSavedOfUser(@PathVariable(value = "email") String email) {
//	    if (!userRepository.existsByEmail(email)) {
//	      throw new RuntimeException("Not found email = " + email);
//	    }
//	    AppUser currentUser = userRepository.findByEmail(email);
//	    bikeRepository.deleteByUser(currentUser);
//	    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//	  }
}

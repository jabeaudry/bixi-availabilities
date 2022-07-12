package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.example.auth.model.AppUser;
import com.example.auth.repository.UserRepo;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)  //commit to db
public class UserRepoTests {
	
	@Autowired
	private UserRepo repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateUser() {
		//create user
		AppUser u = new AppUser();
		u.setEmail("tom@gmail.com");
		u.setPassword("password");
		u.setFirstName("Tom");
		u.setLastName("Tom");
		
		AppUser savedUser = repo.save(u);
		
		AppUser existUser = entityManager.find(AppUser.class, savedUser.getId());
		
		assertThat(existUser.getEmail()).isEqualTo(u.getEmail());
	}
	
	@Test
	public void testFindUserByEmail() {
		String email = "jacintheb27@gmail.com";
		
		AppUser user = repo.findByEmail(email);
		
		assertThat(user).isNotNull();
	}

}

package com.example.auth.payload.request;


import java.util.Set;

import javax.validation.constraints.*;

public class SignUpRequest {

  @NotBlank
  @Size(max = 45)
  @Email
  private String email;

  @NotBlank
  @Size(min = 6, max = 64)
  private String password;

  @NotBlank
  @Size(max = 20)
  private String firstName;
  
  @NotBlank
  @Size(max = 20)
  private String lastName;
  
  private Set<String> role;
  
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
  
  public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	  public Set<String> getRole() {
	    return this.role;
	  }

	  public void setRole(Set<String> role) {
	    this.role = role;
	  }

}
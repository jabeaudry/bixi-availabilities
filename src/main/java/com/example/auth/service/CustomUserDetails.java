package com.example.auth.service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.auth.model.AppUser;
import com.example.auth.model.SavedStation;
import com.fasterxml.jackson.annotation.JsonIgnore;


public class CustomUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String firstName;
	private String lastName;
	private String email;

	private Collection<? extends GrantedAuthority> authorities;
	@JsonIgnore
	private String password;
	
	
	public CustomUserDetails(Long id, String email, String password, String firstName, String lastName, Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.firstName  =firstName;
		this.lastName = lastName;
		this.authorities = authorities;
	}
	
	public static CustomUserDetails build(AppUser user) {
		List<GrantedAuthority> authorities = user.getRoles().stream()
		        .map(role -> new SimpleGrantedAuthority(role.getName().name()))
		        .collect(Collectors.toList());
		return new CustomUserDetails(
				user.getId(), 
				user.getEmail(),
				user.getPassword(), 
				user.getFirstName(),
				user.getLastName(),
				authorities	);
	}
	

	public Long getId() {
		return id;
	}
	public String getEmail() {
		return email;
	}
	@Override
	public String getPassword() {
		return password;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	
//	public Set<SavedStation> getFaves() {
//		return faves;
//	}
//
//	public void setFaves(Set<SavedStation> faves) {
//		this.faves = faves;
//	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	}
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		CustomUserDetails user = (CustomUserDetails) o;
		return Objects.equals(id, user.id);
	}

	 @Override
	  public Collection<? extends GrantedAuthority> getAuthorities() {
	    return authorities;
	  }

	@Override
	public String getUsername() {
		return email;
	}
}
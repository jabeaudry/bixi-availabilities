package com.example.auth.controller;


import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.auth.exception.TokenRefreshException;
import com.example.auth.model.AppUser;
import com.example.auth.model.ERole;
import com.example.auth.model.RefreshToken;
import com.example.auth.model.Role;
import com.example.auth.payload.request.LoginRequest;
import com.example.auth.payload.request.SignUpRequest;
import com.example.auth.payload.request.TokenRefreshRequest;
import com.example.auth.payload.response.JwtResponse;
import com.example.auth.payload.response.MessageResponse;
import com.example.auth.payload.response.TokenRefreshResponse;
import com.example.auth.repository.RefreshTokenRepository;
import com.example.auth.repository.RoleRepo;
import com.example.auth.repository.UserRepo;
import com.example.auth.repository.BikeRepo;
import com.example.auth.security.JwtUtils;
import com.example.auth.service.CustomUserDetails;
import com.example.auth.service.CustomUserDetailsService;
import com.example.auth.service.RefreshTokenService;



//enable cors
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")  //auth url
public class AuthController {
	
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	UserRepo userRepository;
	@Autowired
	RoleRepo roleRepository;
	@Autowired
	PasswordEncoder encoder;
	@Autowired
	JwtUtils jwtUtils;
	@Autowired
	CustomUserDetailsService userService;
	@Autowired
	RefreshTokenService refreshTokenService;
	
	
	
	@GetMapping("/users/{email}")
	public UserDetails getUser(@PathVariable("email") String email) {
		return userService.loadUserByUsername(email);
	}
	
	
	@PostMapping("/signin")
	 public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
	    Authentication authentication = authenticationManager
	        .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
	    SecurityContextHolder.getContext().setAuthentication(authentication);
	    CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
	    String jwt = jwtUtils.generateJwtToken(userDetails);
	    List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
	        .collect(Collectors.toList());
	    RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
		return ResponseEntity.ok(new JwtResponse(jwt, 
												 refreshToken.getToken(),
												 userDetails.getId(), 
												 userDetails.getEmail(), 
												 userDetails.getFirstName(),
												 userDetails.getLastName(),
												 roles
												 ));
	}
	
	@PostMapping("/refreshtoken")
	  public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
	    String requestRefreshToken = request.getRefreshToken();
	    return refreshTokenService.findByToken(requestRefreshToken)
	        .map(refreshTokenService::verifyExpiration)
	        .map(RefreshToken::getUser)
	        .map(user -> {
	          String token = jwtUtils.generateTokenFromUsername(user.getEmail());
	          return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
	        })
	        .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
	            "Refresh token is not in database!"));
	  }
	
	@SuppressWarnings("unused")
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}
		// Create new user's account
		AppUser user = new AppUser( 
							 signUpRequest.getEmail(),
							 encoder.encode(signUpRequest.getPassword()),
							signUpRequest.getFirstName(),
							signUpRequest.getLastName());
		
//		 Set<String> strRoles = signUpRequest.getRole();
		Set<String> strRoles = new HashSet<String>();
		strRoles.add("user");
		 Set<Role> roles = new HashSet<>();
		 if (strRoles == null) {
		      Role userRole = roleRepository.findByName(ERole.ROLE_USER)
		          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		      roles.add(userRole);
		    } else {
		      strRoles.forEach(role -> {
		        switch (role) {
		        case "admin":
		          Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
		              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		          roles.add(adminRole);

		          break;
		        case "mod":
		          Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
		              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		          roles.add(modRole);

		          break;
		        default:
		          Role userRole = roleRepository.findByName(ERole.ROLE_USER)
		              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		          roles.add(userRole);
		        }
		      });
		    }

		    user.setRoles(roles);
		
		userRepository.save(user);
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
}
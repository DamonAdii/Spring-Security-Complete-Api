package com.securityapi.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.securityapi.config.JwtService;
import com.securityapi.user.Role;
import com.securityapi.user.User;
import com.securityapi.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	
	  private final UserRepository repository;
	  private final PasswordEncoder passwordEncoder;
	  private final JwtService jwtService;
	  private final AuthenticationManager authenticationManager;
	
	
	public AuthenticationResponse register(RegisterRequest request) {
		 
		var user = User.builder()
		        .firstname(request.getFirstname())
		        .lastname(request.getLastname())
		        .email(request.getEmail())
		        .password(passwordEncoder.encode(request.getPassword()))
		        .role(Role.USER)
		        .build();
		
		repository.save(user);
		
		var jwtToken = jwtService.generateToken(user);
		
		return AuthenticationResponse.builder()
				 .accessToken(jwtToken)
				 .build();
	 }
	
	
	
	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		
		authenticationManager.authenticate( 
				
				new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
				
				);
		
		var user = repository.findByEmail(request.getEmail())
		        .orElseThrow(()-> new RuntimeException("User not found"));
		
		var jwtToken = jwtService.generateToken(user);
		
		return AuthenticationResponse.builder()
				 .accessToken(jwtToken)
				 .build();
		
	}

}

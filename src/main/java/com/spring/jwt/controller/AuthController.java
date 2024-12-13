package com.spring.jwt.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.jwt.dto.JwtAuthenticationResponse;
import com.spring.jwt.dto.RefreshTokenRequest;
import com.spring.jwt.dto.SignIn;
import com.spring.jwt.dto.SignUp;
import com.spring.jwt.entity.User;
import com.spring.jwt.service.Impl.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
	
	private final AuthService authService;
	
	
	@PostMapping("/signup")
	public ResponseEntity<?> signUpCustomer(@RequestBody SignUp signUpRequest) {
	    if (authService.hasCustomerWithEmail(signUpRequest.getEmail())) {
	        return ResponseEntity.status(HttpStatus.CONFLICT).body("Customer with this email id is already registered.");
	    }
	    User createdUser = authService.createCustomer(signUpRequest);
	    return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
	}

	
	@PostMapping("/signin")
	public ResponseEntity<JwtAuthenticationResponse> signInCustomer(@RequestBody SignIn signInRequest) {
		return ResponseEntity.ok(authService.signIn(signInRequest));
	
	}
	
	@PostMapping("/refesh")
	public ResponseEntity<JwtAuthenticationResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest) {
		return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest));
	
	}

}
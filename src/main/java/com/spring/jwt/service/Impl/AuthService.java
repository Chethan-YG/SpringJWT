package com.spring.jwt.service.Impl;

import com.spring.jwt.dto.JwtAuthenticationResponse;
import com.spring.jwt.dto.RefreshTokenRequest;
import com.spring.jwt.dto.SignIn;
import com.spring.jwt.dto.SignUp;
import com.spring.jwt.entity.User;

public interface AuthService {
	
	User createCustomer(SignUp signUpRequest);
	boolean hasCustomerWithEmail(String email);
	JwtAuthenticationResponse signIn(SignIn signin);
	JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

}

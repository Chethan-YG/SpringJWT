package com.spring.jwt.service.Impl;

import java.util.HashMap;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.jwt.dto.JwtAuthenticationResponse;
import com.spring.jwt.dto.RefreshTokenRequest;
import com.spring.jwt.dto.SignIn;
import com.spring.jwt.dto.SignUp;
import com.spring.jwt.entity.Role;
import com.spring.jwt.entity.User;
import com.spring.jwt.repository.UserRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
	
    @PostConstruct
    public void createAdminAccount() {
        User adminAccount = userRepository.findByRole(Role.ADMIN);
        if (adminAccount == null) {
            User newAdminAccount = new User();
            newAdminAccount.setName("ADMIN");
            newAdminAccount.setEmail("admin57@gmail.com");
            newAdminAccount.setPassword(passwordEncoder.encode("admin57"));
            newAdminAccount.setRole(Role.ADMIN);
            userRepository.save(newAdminAccount);
        }
    }

    @Override
    public User createCustomer(SignUp signUpRequest) {
        User user = new User();
        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setRole(Role.USER);
        return userRepository.save(user);
    }

    @Override
    public boolean hasCustomerWithEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
    
    public JwtAuthenticationResponse signIn(SignIn signin)
    {
    	authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signin.getEmail(), signin.getPassword()));
    	var user=userRepository.findByEmail(signin.getEmail()).orElseThrow(()-> new IllegalArgumentException("Invalid Email or Password"));
		var jwt=jwtService.generateToken(user);
		var refereshToken=jwtService.generateRefreshToken(new HashMap<>(), user);
		JwtAuthenticationResponse jwtAuthenticationResponse=new JwtAuthenticationResponse();
		jwtAuthenticationResponse.setToken(jwt);
		jwtAuthenticationResponse.setRefreshToken(refereshToken);
    	return jwtAuthenticationResponse;
    	
    }

    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest)
    {
    	String email=jwtService.extractUserName(refreshTokenRequest.getToken());
    	User user=userRepository.findUserByEmail(email);
    	if(jwtService.isTokenValid(refreshTokenRequest.getToken(), user))
    	{
    		var jwt =jwtService.generateToken(user);
    		JwtAuthenticationResponse jwtAuthenticationResponse=new JwtAuthenticationResponse();
    		jwtAuthenticationResponse.setToken(jwt);
    		jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
        	return jwtAuthenticationResponse;
    	}
    	return null;
    }

}

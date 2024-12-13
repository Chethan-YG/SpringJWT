package com.spring.jwt.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin
@RequiredArgsConstructor
public class AdminController {
	
	@GetMapping("/hello")
	public String Greet() {
		return "Hello admin";
	}

}

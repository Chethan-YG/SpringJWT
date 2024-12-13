package com.spring.jwt.dto;

import lombok.Data;

@Data
public class SignUp {
	
	private String name;
	private String email;
	private String password;
	public String phNo;

}

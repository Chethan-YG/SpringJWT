package com.spring.jwt.dto;


import com.spring.jwt.entity.Role;

import lombok.Data;

@Data
public class Userdto {
	private Integer id;

	private String name;

	private String email;

	private Role userRole;

}

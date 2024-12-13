package com.spring.jwt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.jwt.entity.Role;
import com.spring.jwt.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	Optional<User> findByEmail(String email);
	User findUserByEmail(String email);
	User findByRole(Role role);

}

package com.narasimham.springbootsecuritydemo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.narasimham.springbootsecuritydemo.entity.*;
import com.narasimham.springbootsecuritydemo.repository.*;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	public Optional<User> findUserById(int id) {
		return userRepository.findById(id);
	}
	
//	public User findUserByEmail(String email) {
//		return userRepository.findByEmail(email);
//	}
	
	public User signupUser(User user) {
		return userRepository.save(user);
	}
}

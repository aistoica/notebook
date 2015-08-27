package com.ansr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ansr.dto.User;
import com.ansr.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public User saveUser(User u) {
		return userRepository.save(u);
	}
}

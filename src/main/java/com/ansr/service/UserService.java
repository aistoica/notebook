package com.ansr.service;

import java.util.List;

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

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public User getUser(String userId) {
		return userRepository.findOne(userId);
	}

	public void delete(String userId) {
		userRepository.delete(userId);
	}
}

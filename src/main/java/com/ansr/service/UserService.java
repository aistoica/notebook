package com.ansr.service;

import java.util.List;

import com.ansr.exceptions.common.ApplicationException;
import com.ansr.exceptions.UserErrorCodes;
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

		User user = userRepository.findOne(userId);
		if(user == null) {
			throw new ApplicationException(UserErrorCodes.USER_NOT_FOUND);
		}

		return user;
	}

	public void delete(String userId) {

		User user = userRepository.findOne(userId);
		if(user == null) {
			throw new ApplicationException(UserErrorCodes.USER_NOT_FOUND);
		}

		userRepository.delete(userId);
	}

	public void updateUser(String userId, User user) {

		User existingUser = userRepository.findOne(userId);
		if(existingUser == null) {
			throw new ApplicationException(UserErrorCodes.USER_NOT_FOUND);
		}

		userRepository.save(user);
	}
}

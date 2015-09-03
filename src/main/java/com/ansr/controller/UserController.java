package com.ansr.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ansr.dto.MaritalStatus;
import com.ansr.dto.User;
import com.ansr.service.StorageService;
import com.ansr.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@ResponseBody
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private StorageService storageService;

	@Autowired
	GridFsTemplate gridFsTemplate;

	// Create new user
	@RequestMapping(value = "/users/new", method = RequestMethod.POST)
	public @ResponseBody String saveUserRestful(@RequestBody User user) {
		//
		// Code processing the input parameters
		//
		String response = "{\"message\":\"Post With ngResource: The user firstname: " + user.getFirstName()
				+ ", lastname: " + user.getLastName() + "birth city" + user.getBirthAddress().getCity() + "\"}";

		userService.saveUser(user);

		return response;
	}

	// Get all users
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public String getAllUsers() {
		List<User> userList = userService.getAllUsers();

		ObjectMapper objectMapper = new ObjectMapper();
		String jsonData = null;
		try {
			jsonData = objectMapper.writeValueAsString(userList);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return jsonData;
	}

	//Get user
	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
	public String getUsersById(@PathVariable("id") String userId) {
		User user = userService.getUser(userId);

		ObjectMapper objectMapper = new ObjectMapper();
		String jsonData = null;
		try {
			jsonData = objectMapper.writeValueAsString(user);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return jsonData;
	}

	// Update user
	@RequestMapping(value = "/users/{id}", method = RequestMethod.POST)
	public String updateUser(@PathVariable("id") String userId, @RequestBody User user) {
		String response = "{\"message\":\"Edited: The user firstname: " + user.getFirstName() + ", lastname: "
				+ user.getLastName() + "birth city" + user.getBirthAddress().getCity() + "\"}";
		userService.saveUser(user);
		return response;

	}

	// Delete user
	@RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
	public void deleteUser(@PathVariable("id") String userId) {
		this.userService.delete(userId);
	}

	// Upload files to user
	@RequestMapping(value = "/upload/{id}", method = RequestMethod.POST)
	public String uploadFiles(@RequestParam("file") MultipartFile[] files, @PathVariable("id") String userId) {
		String msg = "";
		String fileName = null;

		if (files != null && files.length > 0) {
			for (int i = 0; i < files.length; i++) {
				try {
					fileName = files[i].getOriginalFilename();
					String storedId = storageService.save(files[i].getInputStream(), files[i].getContentType(),
							files[i].getOriginalFilename(), userId);
					msg += "You have successfully uploaded " + fileName + "<br/> with id " + storedId;
				} catch (IOException e) {
					return "You failed to upload " + fileName + ": " + e.getMessage() + "<br/>";
				}
			}
			return msg;
		} else {
			return "Unable to upload. File is empty.";
		}
	}

	@RequestMapping("/maritalStatus")
	public String[] getMaritalStatus() {
		MaritalStatus[] status = MaritalStatus.values();
		String[] stringStatus = new String[status.length];
		for (int i = 0; i < status.length; i++) {
			stringStatus[i] = status[i].getValue();
		}
		return stringStatus;
	}

}

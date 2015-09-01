package com.ansr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ansr.dto.MaritalStatus;
import com.ansr.dto.User;
import com.ansr.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Controller
@ResponseBody
public class UserController {

	@Autowired
	private UserService userService;
	
	// Create a new user
	//
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
	
	@RequestMapping("/maritalStatus")
	public String[] getMaritalStatus() {
		MaritalStatus[] status = MaritalStatus.values();
		String[] stringStatus = new String[status.length];
		for(int i = 0; i < status.length; i++) {
			stringStatus[i] = status[i].getValue();
		}
		return stringStatus;
	}
	
	@RequestMapping(value = "/users/all", method = RequestMethod.GET)
	public String getAllUsers() {
		List<User> userList = userService.getAllUsers();
		
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonData = null;
		try {
			jsonData = objectMapper.writeValueAsString(userList);
		} catch (JsonProcessingException e) {;
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return jsonData;
	}
	
	@RequestMapping(value = "/users/edit/{id}", method = RequestMethod.GET)    
    public String getUsersById( @PathVariable("id") String userId )   {        
        User user = userService.getUser(userId);
		
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonData = null;
		try {
			jsonData = objectMapper.writeValueAsString(user);
		} catch (JsonProcessingException e) {;
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return jsonData;
    }
	
	@RequestMapping(value = "/users/edit/{id}", method = RequestMethod.POST)    
    public String updateUser( @PathVariable("id") String userId,  User user)   { 
		String response = "{\"message\":\"Edited: The user firstname: " + user.getFirstName()
		+ ", lastname: " + user.getLastName() + "birth city" + user.getBirthAddress().getCity() + "\"}";
		userService.saveUser(user);
		return response;
		
	}
	
}

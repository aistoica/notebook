package com.ansr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ansr.dto.MaritalStatus;
import com.ansr.dto.User;
import com.ansr.service.UserService;


@Controller
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
	@ResponseBody
	public String[] getMaritalStatus() {
		MaritalStatus[] status = MaritalStatus.values();
		String[] stringStatus = new String[status.length];
		for(int i = 0; i < status.length; i++) {
			stringStatus[i] = status[i].getValue();
		}
		return stringStatus;
	}	
}

package com.ansr.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ansr.dto.Role;
import com.ansr.dto.User;
import com.ansr.service.StorageService;
import com.ansr.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.gridfs.GridFSDBFile;

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
	public @ResponseBody String saveUser(@RequestBody User user) {

		String response = "{\"message\":\"Post With ngResource: The user firstname: " + user.getFirstName()
				+ ", lastname: " + user.getLastName() + "birth city" + user.getBirthAddress().getCity() + "\"}";

		user.setRole(Role.USER);
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

	// Get user
	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
	public String getUsersById(@PathVariable("id") String userId) {
		User user = userService.getUser(userId);

		//convert object to json
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

	// Get uploaded files for user
	@RequestMapping(value = "/upload/{id}", method = RequestMethod.GET)
	public String getUploadedFilesName(@PathVariable("id") String userId) {

		List<String> filesName = storageService.getFilesNameByUser(userId);
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonData = null;
		try {
			jsonData = objectMapper.writeValueAsString(filesName);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return jsonData;
	}

	// Upload profile photo
	@RequestMapping(value = "/uploadPhoto/{id}", method = RequestMethod.POST)
	public String uploadPhoto(@RequestParam("file") MultipartFile file, @PathVariable("id") String userId) {
		String msg = "";
		String fileName = null;

		if (file != null) {
			try {
				fileName = file.getOriginalFilename();
				String storedId = storageService.save(file.getInputStream(), file.getContentType(),
						file.getOriginalFilename(), userId);
				msg += "You have successfully uploaded " + fileName + "<br/> with id " + storedId;

				User user = userService.getUser(userId);
				user.setPhoto(fileName);
				userService.saveUser(user);

			} catch (IOException e) {
				return "You failed to upload " + fileName + ": " + e.getMessage() + "<br/>";
			}

			return msg;
		} else {
			return "Unable to upload. File is empty.";
		}
	}

	// Download profile photo
	@RequestMapping(value = "/downloadPhoto/{id}/{name:.+}", method = RequestMethod.GET)
	public ResponseEntity<String> downloadPhoto(@PathVariable("id") String userId, @PathVariable("name") String fileName) {

		//set the headers
		HttpHeaders headers = new HttpHeaders();
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");
		
		//get file from db
		GridFSDBFile file = storageService.getFileByUserAndName(userId, fileName);

		//if file doesn't exist, return 404
		if(file == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The file was not found");
		}
		
		//get file content
		InputStream content = file.getInputStream();
		
		//convert content to byte array
		byte[] bytes = null;
		try {
			bytes = IOUtils.toByteArray(content);
		} catch (IOException e) {
			e.printStackTrace();
		}

		//encode the content as string base 64 to use data uri
		String encodedContent = "data:image/xyz;base64," + Base64.encodeBase64String(bytes);

		//set the content type: any type of image and the content length
		headers.setContentType(MediaType.valueOf("image/xyz"));
		headers.setContentLength(encodedContent.length());

		//return the encoded image
		return ResponseEntity.ok().headers(headers).body(encodedContent);

	}

	// Download file
	@RequestMapping(value = "/download/{id}/{name:.+}", method = RequestMethod.GET)
	public ResponseEntity<InputStreamResource> downloadFile(@PathVariable("id") String userId,
			@PathVariable("name") String fileName) {
		
		GridFSDBFile file = storageService.getFileByUserAndName(userId, fileName);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");

		return ResponseEntity.ok().headers(headers).contentLength(file.getLength())
				.contentType(MediaType.parseMediaType("application/octet-stream"))
				.body(new InputStreamResource(file.getInputStream()));
	}


/*	@RequestMapping("/maritalStatus")
	public String[] getMaritalStatus() {
		MaritalStatus[] status = MaritalStatus.values();
		String[] stringStatus = new String[status.length];
		for (int i = 0; i < status.length; i++) {
			stringStatus[i] = status[i].getValue();
		}
		return stringStatus;
	}*/

}

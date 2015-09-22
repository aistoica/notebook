package com.ansr.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ansr.dto.User;
import com.ansr.service.StorageService;
import com.ansr.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.gridfs.GridFSDBFile;

@Controller
@ResponseBody
@RequestMapping("/users")
public class UserController extends BaseController {

	@Autowired
	private UserService userService;

	@Autowired
	private StorageService storageService;

	// Create new user
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity saveUser(@RequestBody @Valid User user) {

		User userCreated = userService.saveUser(user);

		Map<String, String> response = new HashMap<>();
		response.put("id", userCreated.getId());
		return prepareResponse(response);
	}

	// Get all users
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity getAllUsers() {

		List<User> userList = userService.getAllUsers();
		return prepareResponse(userList);
	}

	// Get user
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity getUsersById(@PathVariable("id") String userId) {

		User user = userService.getUser(userId);
		return prepareResponse(user);
	}

	// Update user
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity updateUser(@PathVariable("id") String userId, @RequestBody User user) {

		userService.updateUser(userId, user);

		Map<String, String> response = new HashMap<>();
		response.put("id", userId);
		return prepareResponse(response);
	}

	// Delete user
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity deleteUser(@PathVariable("id") String userId) {

		storageService.deleteAllFilesByUser(userId);
		userService.delete(userId);

		Map<String, String> response = new HashMap<>();
		response.put("id", userId);
		return prepareResponse(response);
	}

	// Upload files to user
	@RequestMapping(value = "/upload/{id}", method = RequestMethod.POST)
	public String uploadFiles(@RequestParam("file") MultipartFile[] files, @PathVariable("id") String userId) {
		String msg = "";
		String fileName = null;

		if (files != null && files.length > 0) {
			for (int i = 0; i < files.length; i++) {
				fileName = files[i].getOriginalFilename();
				String storedId = storageService.save(files[i], userId);
				msg += "You have successfully uploaded " + fileName + "with id " + storedId;
			}
		} else {
			msg =  "Unable to upload. File is empty.";
		}

		return "{\"result\": \"" + msg +"\"}";
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
	public ResponseEntity uploadPhoto(@RequestParam("file") MultipartFile file, @PathVariable("id") String userId) {
		String msg = "";
		String fileName = null;

		if (file != null) {
			fileName = file.getOriginalFilename();
			String storedId = storageService.save(file, userId);
			msg += "You have successfully uploaded " + fileName + "<br/> with id " + storedId;

			User user = userService.getUser(userId);
			user.setPhoto(fileName);
			userService.saveUser(user);
		} else {
			msg = "Unable to upload. File is empty.";
		}

		Map<String, String> response = new HashMap<>();
		response.put("result", msg);
		return prepareResponse(response);
	}

	// Download profile photo
	@RequestMapping(value = "/downloadPhoto/{id}/{name:.+}", method = RequestMethod.GET)
	public ResponseEntity<String> downloadPhoto(@PathVariable("id") String userId,
			@PathVariable("name") String fileName) {

		// set the headers
		HttpHeaders headers = new HttpHeaders();
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");

		// get file from db
		GridFSDBFile file = storageService.getFileByUserAndName(userId, fileName);

		// if file doesn't exist, return 404
		if (file == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The file was not found");
		}

		// get file content
		InputStream content = file.getInputStream();

		// get file type
		String contentType = FilenameUtils.getExtension(fileName);
		// convert content to byte array
		byte[] bytes = null;
		try {
			bytes = IOUtils.toByteArray(content);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// encode the content as string base 64 to use data uri
		String encodedContent = "data:image/" + contentType + ";base64," + Base64.encodeBase64String(bytes);

		// set the content type: any type of image and the content length
		headers.setContentType(MediaType.valueOf("image/xyz"));
		headers.setContentLength(encodedContent.length());

		// return the encoded image
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
}

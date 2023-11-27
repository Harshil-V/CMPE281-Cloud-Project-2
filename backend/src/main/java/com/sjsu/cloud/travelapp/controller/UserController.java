package com.sjsu.cloud.travelapp.controller;

import com.sjsu.cloud.travelapp.entity.UserEntity;
import com.sjsu.cloud.travelapp.model.User;
import com.sjsu.cloud.travelapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller 
@RequestMapping(path="/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
	@Autowired
	private UserService userService;
	
	@PostMapping(path="/addUser")
	public ResponseEntity<?> addUser (@RequestBody UserEntity userEntity) {
		return userService.addUser(userEntity);
	}

	@PostMapping(path="/authenticateUser")
	public ResponseEntity<String> authenticateUser (@RequestBody User user) {
		return userService.authenticateUserLogin(user);
	}

	@PostMapping(path="/updateUser")
	public ResponseEntity<?> updateUser (@RequestBody UserEntity userEntity) {
		return userService.updateUser(userEntity);
	}

	@PostMapping(path="/deleteUser")
	public ResponseEntity<?> deleteUser (@RequestBody UserEntity userEntity) {
		return userService.deleteUser(userEntity);
	}

	@RequestMapping(path="/allUsers")
	public ResponseEntity<?> getAllUsers () {
		return userService.getAllUsers();
	}

	@PostMapping(path="/getUser")
	public ResponseEntity<?> getUser (@RequestBody User user) {
		return userService.getUser(user);
	}
}

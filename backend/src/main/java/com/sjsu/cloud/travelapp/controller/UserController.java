package com.sjsu.cloud.travelapp.controller;

import com.sjsu.cloud.travelapp.entity.UserEntity;
import com.sjsu.cloud.travelapp.model.User;
import com.sjsu.cloud.travelapp.repository.UserRepository;
import com.sjsu.cloud.travelapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller 
@RequestMapping(path="/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
	@PostMapping(path="/addUser")
	public @ResponseBody String addNewUser (@RequestBody UserEntity myUser) {
	    userRepository.save(myUser);
	    return "User Added Successfully !!";
	  }
	
	@PostMapping(path="/authenticateUser")
	public @ResponseBody UserEntity authenticateUser (@RequestBody User user) {
	   	UserEntity myUser = userService.authenticateUserLogin(user);
	    return myUser;
	  }
	
	@RequestMapping(path="/allUsers")
	public @ResponseBody List<UserEntity> getAllUsers () {
	    return userService.getAllUsers();
	  }
}

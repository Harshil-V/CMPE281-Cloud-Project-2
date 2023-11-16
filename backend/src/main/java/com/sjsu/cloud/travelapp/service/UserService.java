package com.sjsu.cloud.travelapp.service;

import com.sjsu.cloud.travelapp.entity.UserEntity;
import com.sjsu.cloud.travelapp.model.User;
import com.sjsu.cloud.travelapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
		
	public UserEntity authenticateUserLogin(User user) {
	  UserEntity userEntity =  userRepository.findByUserEmail(user.getUserEmail());

	  if(userEntity==null) {
		  System.out.println("Null userEntity");
	  }	  
	 return userEntity;  
	}

	public List<UserEntity> getAllUsers() {		
		List userList = new ArrayList<UserEntity>();		
		userRepository.findAll().forEach(userList::add);
		return userList;
	}
}

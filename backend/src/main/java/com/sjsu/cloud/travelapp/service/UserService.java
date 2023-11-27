package com.sjsu.cloud.travelapp.service;

import com.sjsu.cloud.travelapp.entity.UserEntity;
import com.sjsu.cloud.travelapp.model.User;
import com.sjsu.cloud.travelapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.dao.DataAccessException;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public ResponseEntity<String> authenticateUserLogin(User user) {
		try {
			UserEntity userEntity = userRepository.findByUserEmail(user.getUserEmail());
			if (userEntity == null) {
				return new ResponseEntity<>("Email or password not found!", HttpStatus.NOT_FOUND);
			} else if (userEntity.getUserPassword().equals(user.getUserPassword())){
				return new ResponseEntity<>("Login successful!", HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Email or password does not match!", HttpStatus.FORBIDDEN);
			}
		} catch (DataAccessException e) {
			handleException("Error during user login", e);
			return new ResponseEntity<>("An error occurred during login.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<?> getAllUsers() {
		try {
			List<UserEntity> userList = new ArrayList<>();
			userRepository.findAll().forEach(userList::add);
			return new ResponseEntity<>(userList, HttpStatus.OK);
		} catch (DataAccessException e) {
			handleException("Error while fetching all users", e);
			return new ResponseEntity<>("An error occurred while fetching all users.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<?> getUser(User user) {
		try {
			UserEntity userEntity = userRepository.findByUserEmail(user.getUserEmail());
			if (userEntity == null) {
				return new ResponseEntity<>("User not found!", HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity<>(userEntity, HttpStatus.OK);
			}
		} catch (DataAccessException e) {
			handleException("Error while getting user", e);
			return new ResponseEntity<>("An error occurred while fetching user.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<?> addUser(UserEntity userEntity) {
		try {
			userRepository.save(userEntity);
			System.out.println("User added successfully!");
			return new ResponseEntity<>("User added successfully!", HttpStatus.CREATED);
		} catch (DataAccessException e) {
			handleException("Error while adding user", e);
			return new ResponseEntity<>("An error occurred while adding user.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<?> updateUser(UserEntity userEntity) {
		try {
			userRepository.save(userEntity);
			System.out.println("User updated successfully!");
			return new ResponseEntity<>("User updated successfully!", HttpStatus.OK);
		} catch (DataAccessException e) {
			handleException("Error while updating user", e);
			return new ResponseEntity<>("An error occurred while updating user.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<?> deleteUser(UserEntity userEntity) {
		try {
			userRepository.delete(userEntity);
			System.out.println("User deleted successfully!");
			return new ResponseEntity<>("User deleted successfully!", HttpStatus.OK);
		} catch (DataAccessException e) {
			handleException("Error while deleting user", e);
			return new ResponseEntity<>("An error occurred while deleting user.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private void handleException(String message, DataAccessException e) {
		System.err.println(message + ": " + e.getMessage());
		// TODO - Use CloudWatch Logs here
	}
}
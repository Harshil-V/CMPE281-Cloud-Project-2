package com.sjsu.cloud.travelapp.service;

import com.sjsu.cloud.travelapp.model.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sjsu.cloud.travelapp.entity.AdminEntity;
import com.sjsu.cloud.travelapp.repository.AdminRepository;

@Service
public class AdminService {

	@Autowired
	private AdminRepository adminRepository;

	public ResponseEntity<String> authenticateAdminLogin(Admin admin) {
		AdminEntity adminEntity = adminRepository.findByAdminEmail(admin.getAdminEmail());
		if (adminEntity == null) {
			return new ResponseEntity<>("Email or password does not exist!", HttpStatus.NOT_FOUND);
		} else if (adminEntity.getAdminPassword().equals(admin.getAdminPassword())) {
			return new ResponseEntity<>("Login successful!", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Email or password does not match!", HttpStatus.FORBIDDEN);
		}
	}

	public ResponseEntity<?> addAdmin(AdminEntity adminEntity) {
		try {
			adminRepository.save(adminEntity);
			System.out.println("Admin added successfully!");
			return new ResponseEntity<>("Admin added successfully!", HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>("An error occurred while adding admin.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<?> updateAdmin(AdminEntity adminEntity) {
		try {
			adminRepository.save(adminEntity);
			System.out.println("Admin updated successfully!");
			return new ResponseEntity<>("Admin updated successfully!", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("An error occurred while updating admin.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<?> deleteAdmin(AdminEntity adminEntity) {
		try {
			adminRepository.delete(adminEntity);
			System.out.println("Admin deleted successfully!");
			return new ResponseEntity<>("Admin deleted successfully!", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("An error occurred while deleting admin.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
package com.sjsu.cloud.travelapp.controller;

import com.sjsu.cloud.travelapp.model.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sjsu.cloud.travelapp.entity.AdminEntity;
import com.sjsu.cloud.travelapp.service.AdminService;

@Controller 
@RequestMapping(path="/admin")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AdminController {
	@Autowired
	private AdminService adminService;
	
	@PostMapping(path="/addAdmin")
	public ResponseEntity<?> addAdmin(@RequestBody AdminEntity adminEntity) {
		return adminService.addAdmin(adminEntity);
	}

	@PostMapping(path="/authenticateAdmin")
	public ResponseEntity<String> authenticateAdmin(@RequestBody Admin admin) {
		return adminService.authenticateAdminLogin(admin);
	}

	@PostMapping(path="/updateAdmin")
	public ResponseEntity<?> updateAdmin(@RequestBody AdminEntity adminEntity) {
		return adminService.updateAdmin(adminEntity);
	}

	@PostMapping(path="/deleteAdmin")
	public ResponseEntity<?> deleteAdmin(@RequestBody AdminEntity adminEntity) {
		return adminService.deleteAdmin(adminEntity);
	}
}

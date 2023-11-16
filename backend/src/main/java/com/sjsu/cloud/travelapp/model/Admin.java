package com.sjsu.cloud.travelapp.model;

public class Admin {
	private String adminEmail;
    private String adminFirstName;
	private String adminLastName;
    private String adminPassword;
    
    /**
	 * @param adminEmail
	 * @param adminFirstName
	 * @param adminLastName
	 * @param adminPassword
	 */
	public Admin(String adminEmail, String adminFirstName, String adminLastName, String adminPassword) {
		super();
		this.adminEmail = adminEmail;
		this.adminFirstName = adminFirstName;
		this.adminLastName = adminLastName;
		this.adminPassword = adminPassword;
	}
	
	public Admin() {
		super();
	}
	
	public String getAdminEmail() {
		return adminEmail;
	}
	
	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}
	
	public String getAdminFirstName() {
		return adminFirstName;
	}
	
	public void setAdminFirstName(String adminFirstName) {
		this.adminFirstName = adminFirstName;
	}
	
	public String getAdminLastName() {
		return adminLastName;
	}
	
	public void setAdminLastName(String adminLastName) {
		this.adminLastName = adminLastName;
	}
	
	public String getAdminPassword() {
		return adminPassword;
	}
	
	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}
}

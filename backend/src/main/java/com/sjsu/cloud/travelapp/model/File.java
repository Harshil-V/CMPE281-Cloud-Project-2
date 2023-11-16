package com.sjsu.cloud.travelapp.model;

public class File {
	
	private Long fileID;
    private String fileName;
    private String fileDescription;
    private String fileURL;
    private String uploadTime;
    private String updateTime;
    private User user;
   
	public File() {
		super();
	}

	/**
	 * @param fileID
	 * @param fileName
	 * @param fileDescription
	 * @param fileURL
	 * @param uploadTime
	 * @param updateTime
	 */
	public File(Long fileID, String fileName, String fileDescription, String fileURL, String uploadTime,
			String updateTime,User user) {
		super();
		this.fileID = fileID;
		this.fileName = fileName;
		this.fileDescription = fileDescription;
		this.fileURL = fileURL;
		this.uploadTime = uploadTime;
		this.updateTime = updateTime;
		this.user = user;
	}
    
	public Long getFileID() {
		return fileID;
	}
	
	public void setFileID(Long fileID) {
		this.fileID = fileID;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getFileDescription() {
		return fileDescription;
	}
	
	public void setFileDescription(String fileDescription) {
		this.fileDescription = fileDescription;
	}
	
	public String getFileURL() {
		return fileURL;
	}
	
	public void setFileURL(String fileURL) {
		this.fileURL = fileURL;
	}
	
	public String getUploadTime() {
		return uploadTime;
	}
	
	public void setUploadTime(String uploadTime) {
		this.uploadTime = uploadTime;
	}
	
	public String getUpdateTime() {
		return updateTime;
	}
	
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}

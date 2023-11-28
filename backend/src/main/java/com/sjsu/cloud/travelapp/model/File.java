package com.sjsu.cloud.travelapp.model;

import java.util.List;

public class File {

    private String fileName;
    private String fileDescription;
    private String fileURL;
	private String versionNo;
    private String uploadTime;
    private String updateTime;
    private String userEmail;
	private List<String> tags;

	public File() {
		super();
	}

	/**
	 * @param fileName
	 * @param fileDescription
	 * @param fileURL
	 * @param uploadTime
	 * @param updateTime
	 * @param userEmail
	 * @param tags
	 */
	public File(String fileName, String fileDescription, String fileURL, String uploadTime,
			String updateTime,String userEmail, List<String> tags) {
		super();
		this.fileName = fileName;
		this.fileDescription = fileDescription;
		this.fileURL = fileURL;
		this.uploadTime = uploadTime;
		this.updateTime = updateTime;
		this.userEmail = userEmail;
		this.tags = tags;
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

	public String getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}
}

package com.sjsu.cloud.travelapp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "file")
public class FileEntity {
	@Id
	@Column(name="file_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long fileId;
	
	@Column(name="file_name")
	private String fileName;
	
	@Column(name="file_desc")
	private String fileDesc;
	
	@Column(name="file_url")
	private String fileURL;
	
	@Column(name="version_no")
	private String versionNo;
	
	@Column(name="upload_date")
	private String uploadDate;

	@Column(name="update_date")
	private String updateDate;
	
	@Column(name="user_email")
	private String userEmail;

	public FileEntity() {
		super();
	}

	/**
	 * @param fileId
	 * @param fileName
	 * @param fileDesc
	 * @param fileURL
	 * @param versionNo
	 * @param uploadDate
	 * @param updateDate
	 * @param userEmail
	 */
	public FileEntity(Long fileId, String fileName, String fileDesc, String fileURL, String versionNo, String uploadDate,
					  String updateDate, String userEmail) {
		super();
		this.fileId = fileId;
		this.fileName = fileName;
		this.fileDesc = fileDesc;
		this.fileURL = fileURL;
		this.versionNo = versionNo;
		this.uploadDate = uploadDate;
		this.updateDate = updateDate;
		this.userEmail = userEmail;
	}

	@Override
	public String toString() {
		return "FileEntity [fileId=" + fileId + ", fileName=" + fileName + ", fileDesc=" + fileDesc + ", fileURL="
				+ fileURL + ", versionNo=" + versionNo + ", uploadDate=" + uploadDate + ", updateDate=" + updateDate
				+ ", userEmail=" + userEmail + "]";
	}

	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileDesc() {
		return fileDesc;
	}

	public void setFileDesc(String fileDesc) {
		this.fileDesc = fileDesc;
	}

	public String getFileURL() {
		return fileURL;
	}

	public void setFileURL(String fileURL) {
		this.fileURL = fileURL;
	}

	public String getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}

	public String getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
}

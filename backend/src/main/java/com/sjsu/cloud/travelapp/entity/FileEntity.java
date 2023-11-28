package com.sjsu.cloud.travelapp.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "file")
public class FileEntity {

	@Id
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

	@ManyToMany
	@JoinTable(
			name = "file_tag",
			joinColumns = @JoinColumn(name = "file_name"),
			inverseJoinColumns = @JoinColumn(name = "tag_id")
	)
	private Set<TagEntity> tags;

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
	 * @param tags
	 */
	public FileEntity(Long fileId, String fileName, String fileDesc, String fileURL, String versionNo, String uploadDate,
					  String updateDate, String userEmail, Set<TagEntity> tags) {
		super();
		this.fileName = fileName;
		this.fileDesc = fileDesc;
		this.fileURL = fileURL;
		this.versionNo = versionNo;
		this.uploadDate = uploadDate;
		this.updateDate = updateDate;
		this.userEmail = userEmail;
		this.tags = tags;
	}

	@Override
	public String toString() {
		return "FileEntity [fileName=" + fileName + ", fileDesc=" + fileDesc + ", fileURL="
				+ fileURL + ", versionNo=" + versionNo + ", uploadDate=" + uploadDate + ", updateDate=" + updateDate
				+ ", userEmail=" + userEmail + "]";
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

	public Set<TagEntity> getTags() {
		return tags;
	}

	public void setTags(Set<TagEntity> tags) {
		this.tags = tags;
	}
}

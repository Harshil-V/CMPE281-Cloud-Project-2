package com.sjsu.cloud.travelapp.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "file_tag-entity")
@Table(name = "file_tag")
public class FileTagEntity {
	@EmbeddedId
	private FileTagId fileTagId;

	public void setFileTagId(FileTagId fileTagId) {
		this.fileTagId = fileTagId;
	}

	public FileTagId getFileTagId() {
		return fileTagId;
	}

	@Embeddable
	public static class FileTagId implements Serializable {

		@Column(name = "file_name")
		private String fileName;

		@Column(name = "tag_id")
		private String tagId;

		public FileTagId() {
		}

		public FileTagId(String fileName, String tagId) {
			this.fileName = fileName;
			this.tagId = tagId;
		}

		public String getFileName() {
			return fileName;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}

		public String getTagId() {
			return tagId;
		}

		public void setTagId(String tagId) {
			this.tagId = tagId;
		}
	}
}

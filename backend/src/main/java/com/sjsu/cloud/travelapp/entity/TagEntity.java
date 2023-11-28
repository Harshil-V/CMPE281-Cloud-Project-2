package com.sjsu.cloud.travelapp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tag")
public class TagEntity {

	@Id
	@Column(name="tag_id")
	private String tagId;

	@Column(name="tag_name")
	private String tagName;

	public TagEntity() {
		super();
	}

	public TagEntity(String tagId, String tagName) {
		this.tagId = tagId;
		this.tagName = tagName;
	}

	public String getTagId() {
		return tagId;
	}

	public void setTagId(String tagId) {
		this.tagId = tagId;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
}

package com.sjsu.cloud.travelapp.repository;

import com.sjsu.cloud.travelapp.entity.TagEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends CrudRepository<TagEntity, String> {
		String findByTagId(String tagId);
}

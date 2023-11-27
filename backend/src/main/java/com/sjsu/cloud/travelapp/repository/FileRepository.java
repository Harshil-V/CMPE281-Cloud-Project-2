package com.sjsu.cloud.travelapp.repository;

import com.sjsu.cloud.travelapp.entity.FileEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends CrudRepository<FileEntity, String> {
		void deleteByFileName(String fileName);
		List findByUserEmail(String userEmail);
		FileEntity findByFileName(String fileName);
}

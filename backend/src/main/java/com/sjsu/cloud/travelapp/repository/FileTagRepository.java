package com.sjsu.cloud.travelapp.repository;

import com.sjsu.cloud.travelapp.entity.FileTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileTagRepository extends JpaRepository<FileTagEntity, FileTagEntity.FileTagId> {

}

package com.sjsu.cloud.travelapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sjsu.cloud.travelapp.entity.FileEntity;

@Repository
public interface FileJPARepository extends JpaRepository<FileEntity, Long> {
	
	@Transactional
	@Modifying
	@Query("UPDATE FileEntity fe SET fe.fileDesc = :fileDesc, fe.updateDate = :updateDate, fe.fileName = :fileName, fe.fileURL = :fileURL WHERE fe.fileId = :fileId")
	void updateFileAbout(@Param(value = "fileId") Long fileId,
						  @Param(value = "updateDate") String updateDate,
						  @Param(value = "fileDesc") String fileDesc,
						  @Param(value = "fileName") String fileName,
						  @Param(value = "fileURL") String fileURL);
	
	@Transactional
	@Modifying
	@Query("UPDATE FileEntity fe SET  fe.updateDate = :updateDate WHERE fe.fileId = :fileId")
	void updateFileDetail(@Param(value = "fileId") Long fileId,
			  			@Param(value = "updateDate") String updateDate);
}

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
	@Query("UPDATE FileEntity fe SET fe.fileDesc = :fileDesc, " +
			"fe.fileURL = :fileURL, fe.versionNo = :versionNo, fe.updateDate = :updateDate, " +
			"fe.userEmail = :userEmail WHERE fe.fileName = :fileName")
	void updateFileDetails(@Param(value = "fileName") String fileName,
							  @Param(value = "fileDesc") String fileDesc,
							  @Param(value = "fileURL") String fileURL,
							  @Param(value = "versionNo") String versionNo,
							  @Param(value = "updateDate") String updateDate,
							  @Param(value = "userEmail") String userEmail);

	@Transactional
	@Modifying
	@Query("UPDATE FileEntity fe SET  fe.updateDate = :updateDate WHERE fe.fileName = :fileName")
	void updateFileDate(@Param(value = "fileName") String fileName,
			  			@Param(value = "updateDate") String updateDate);
}

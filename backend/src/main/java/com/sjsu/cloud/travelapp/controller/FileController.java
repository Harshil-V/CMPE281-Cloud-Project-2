package com.sjsu.cloud.travelapp.controller;

import com.sjsu.cloud.travelapp.entity.FileEntity;
import com.sjsu.cloud.travelapp.model.User;
import com.sjsu.cloud.travelapp.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = "/file")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FileController {

	@Autowired
	private FileService fileService;

	@PostMapping(value= "/uploadFile")
	public ResponseEntity<?> uploadFile(@RequestPart(value = "fileDesc") String fileDesc,
										@RequestPart(value = "versionNo") String versionNo,
										@RequestPart(value = "userEmail") String userEmail,
										@RequestPart(value = "file") final MultipartFile multipartFile) {
		FileEntity fileEntity = new FileEntity();
		fileEntity.setFileDesc(fileDesc);
		fileEntity.setVersionNo(versionNo);
		fileEntity.setUserEmail(userEmail);
		return fileService.uploadFile(multipartFile, fileEntity);
	}

	@GetMapping(value= "/uploadTextractFile")
	public ResponseEntity<?> uploadTextractFile(@RequestPart(value = "file") final MultipartFile multipartFile) {
		return fileService.uploadTextractFile(multipartFile);
	}
	
	@GetMapping(value="/download/{fileName}")
    public ResponseEntity<?> downloadFile(@PathVariable("fileName") String fileName) {
		try {
			return fileService.downloadFileFromS3(fileName);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>("An error occurred while downloading file.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
	
	@DeleteMapping(value="/delete/{fileName}")
    public ResponseEntity<?> deleteFile(@PathVariable("fileName") String fileName) {
		return fileService.deleteFile(fileName);
	}
	
	@GetMapping(value="/getAllFiles")
	public ResponseEntity<?> getAllFiles() throws IOException {
		return fileService.getAllFiles();
    }
	
	@GetMapping(value="/getUserFilesDetails/{userEmail}")
    public ResponseEntity<?> getUserFiles(@PathVariable("userEmail") String userEmail) throws IOException {
		return fileService.getUserFilesDetails(userEmail);
    }

	@GetMapping(value="/getFileDetailsById/{fileName}")
    public ResponseEntity<?> getFileDetailsById(@PathVariable("fileName") String fileName) throws IOException {
		return fileService.getFileDetailsById(fileName);
    }

	@PostMapping(value="/updateFileDetails")
    public ResponseEntity<?> updateFileDetails(@RequestBody FileEntity fileEntity) throws IOException {
		return fileService.updateFileDetails(fileEntity);
    }
	
	@PostMapping(value="/updateFileDate")
    public ResponseEntity<?> updateFileDate(@RequestBody FileEntity fileEntity) throws IOException {
		return fileService.updateFileDate(fileEntity);
    }

	@GetMapping(value="/health")
    public ResponseEntity<?> healthCheck() {
		return new ResponseEntity<>("Travel App is up and running!", HttpStatus.OK);
	}
}

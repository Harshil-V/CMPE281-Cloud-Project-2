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
	public ResponseEntity<?> uploadFile(@RequestPart(value = "fileEntity") FileEntity fileEntity,
										@RequestPart(value = "file") final MultipartFile multipartFile) {
		return fileService.uploadFile(multipartFile, fileEntity);
	}

	@PostMapping(value= "/uploadTextractFile")
	public ResponseEntity<?> uploadTextractFile(@RequestPart(value = "file") final MultipartFile multipartFile) {
		return fileService.uploadTextractFile(multipartFile);
	}
	
	@RequestMapping(value="/download/{fileName}")
    public ResponseEntity<?> downloadFile(@PathVariable("fileName") String fileName) {
		try {
			return fileService.downloadFileFromS3(fileName);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>("An error occurred while downloading file.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
	
	@RequestMapping(value="/delete/{fileName}")
    public ResponseEntity<?> deleteFile(@PathVariable("fileName") String fileName) {
		return fileService.deleteFile(fileName);
	}
	
	@RequestMapping(value="/getAllFiles")
	public ResponseEntity<?> getAllFiles() throws IOException {
		return fileService.getAllFiles();
    }
	
	@RequestMapping(value="/getUserFilesDetails")
    public ResponseEntity<?> getUserFiles(@RequestBody User user) throws IOException {
		return fileService.getUserFilesDetails(user);
    }

	@RequestMapping(value="/getFileDetailsById/{fileName}")
    public ResponseEntity<?> getFileDetailsById(@PathVariable("fileName") String fileName) throws IOException {
		return fileService.getFileDetailsById(fileName);
    }

	@RequestMapping(value="/updateFileDetails")
    public ResponseEntity<?> updateFileDetails(@RequestBody FileEntity fileEntity) throws IOException {
		return fileService.updateFileDetails(fileEntity);
    }
	
	@RequestMapping(value="/updateFileDate")
    public ResponseEntity<?> updateFileDate(@RequestBody FileEntity fileEntity) throws IOException {
		return fileService.updateFileDate(fileEntity);
    }

	@RequestMapping(value="/welcome")
    public @ResponseBody String defaultPage() {
		return "Welcome to Travel App!";
    }
}

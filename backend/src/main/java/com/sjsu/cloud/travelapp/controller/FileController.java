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
import java.util.List;

@RestController
@RequestMapping(value = "/file")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FileController {

	@Autowired
	private FileService fileService;

	@PostMapping(value= "/uploadFile")
    public ResponseEntity<String> uploadFile(@RequestPart(value = "file") final MultipartFile multipartFile){
		System.out.println(multipartFile);
		fileService.uploadFile(multipartFile);
		final String response = "[" + multipartFile.getOriginalFilename() + "] uploaded successfully.";
        return new ResponseEntity<>(response, HttpStatus.OK);
       
    }
	
	@PostMapping(value= "/uploadFileDetails")
    public ResponseEntity<String> uploadFileDetails(@RequestBody FileEntity fileEntity) {
		System.out.println(fileEntity);
		fileService.uploadFileDetails(fileEntity);
        return new ResponseEntity<>("Successfully updated DB", HttpStatus.OK);
       
    }
	
	@RequestMapping(value="/download/{fileName}")
    public String downloadFile(@PathVariable("fileName") String fileName) throws IOException {
		fileService.downloadFileFromS3(fileName);
		return "Download successful !!";
    }
	
	@RequestMapping(value="/delete/{fileName}")
    public String deleteFile(@PathVariable("fileName") String fileName) throws IOException {
		fileService.deleteFile(fileName);
		return "Delete successful !!";
    }
	
	@RequestMapping(value="/viewFiles")
    public @ResponseBody List<FileEntity> viewFiles(@RequestBody User user) throws IOException {
		return fileService.viewAllFiles(user);
    }
	
	@RequestMapping(value="/userView")
    public @ResponseBody List<FileEntity> viewMyFile(@RequestBody User user) throws IOException {
		System.out.println("User email "+ user.getUserEmail());
		return fileService.viewMyFiles(user);
    }

	@RequestMapping(value="/userFile/{fileId}")
    public @ResponseBody FileEntity getMyUserFile(@PathVariable("fileId") Long fileId) throws IOException {
		System.out.println(fileId);
		return fileService.getMyUserFileDetails(fileId);
    }

	@RequestMapping(value="/updateFileAbout")
    public @ResponseBody String updateImageAbout(@RequestBody FileEntity fileEntity) throws IOException {
		System.out.println("Filentity updateFileAbout:"+fileEntity.toString());
		fileService.updateFileAbout(fileEntity);
		return "Updated successfully";
    }
	
	@RequestMapping(value="/updateFileDetail")
    public @ResponseBody String updateImageDetail(@RequestBody FileEntity fileEntity) throws IOException {
		System.out.println("Filentity updateFileDetail:"+fileEntity.toString());
		fileService.updateFileDetail(fileEntity);
		return "Updated successfully";
    }
	
	@RequestMapping(value="/getFiles")
    public List<FileEntity> getAllFiles() throws IOException {
		return fileService.getAllFiles();
    }
	
	@RequestMapping(value="/welcome")
    public @ResponseBody String defaultPage() {
		return "Welcome to Travel App!";
    }
}

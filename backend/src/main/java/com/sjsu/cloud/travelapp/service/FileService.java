package com.sjsu.cloud.travelapp.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.textract.AmazonTextract;
import com.amazonaws.services.textract.model.DetectDocumentTextRequest;
import com.amazonaws.services.textract.model.DetectDocumentTextResult;
import com.amazonaws.services.textract.model.Document;
import com.amazonaws.util.IOUtils;
import com.sjsu.cloud.travelapp.entity.FileEntity;
import com.sjsu.cloud.travelapp.model.User;
import com.sjsu.cloud.travelapp.repository.FileJPARepository;
import com.sjsu.cloud.travelapp.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Transactional
public class FileService{
	
	@Autowired
    private AmazonS3 amazonS3;

	@Autowired
	private AmazonTextract amazonTextract;
	
	@Autowired
	FileRepository fileRepository;
	
	@Autowired
	FileJPARepository fileJPARepository;
	
    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Async
    public void uploadFile(final MultipartFile multipartFile) {
        try {
            final File file = convertMultiPartFileToFile(multipartFile);
            uploadFileToS3Bucket(bucketName, file);
            file.delete();
        } catch (final AmazonServiceException ex) {
        	System.out.println("Error= {} while uploading file."+ ex.getMessage());
        }
    }

	public String uploadTextractFile(final MultipartFile multipartFile) {
		String analyzedText = null;
		try {
			final File file = convertMultiPartFileToFile(multipartFile);
			analyzedText = analyzeTextractDocument(file);
			file.delete();
			
		} catch (final AmazonServiceException | FileNotFoundException ex) {
			System.out.println("Error= {} while analyzing file." + ex.getMessage());
		}
		return analyzedText;
	}

	private String analyzeTextractDocument(File file) throws FileNotFoundException {

		ByteBuffer imageBytes;
		try (InputStream inputStream = new FileInputStream(file)) {
			imageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
		} catch (IOException e) {
            throw new RuntimeException(e);
        }

        AtomicReference<String> analyzedText = new AtomicReference<>("");
		DetectDocumentTextRequest request = new DetectDocumentTextRequest()
				.withDocument(new Document()
						.withBytes(imageBytes));

		DetectDocumentTextResult result = amazonTextract.detectDocumentText(request);
		System.out.println(result);

		result.getBlocks().forEach(block ->{
			if(block.getBlockType().equals("LINE"))
				analyzedText.set("Text is " + block.getText());
		});
		return analyzedText.get();
	}
    
    public void uploadFileDetails(FileEntity fileEntity) {
        try {
           fileRepository.save(fileEntity);
        } catch (final AmazonServiceException ex) {
        	System.out.println("Error= {} while uploading file."+ ex.getMessage());
        }
    }
 
    private File convertMultiPartFileToFile(final MultipartFile multipartFile) {       
    	final File file = new File(multipartFile.getOriginalFilename());
        try (final FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(multipartFile.getBytes());
        } catch (final IOException ex) {
        	System.out.println("Error converting the multi-part file to file= "+ ex.getMessage());
        }
        return file;
    }
 
    private void uploadFileToS3Bucket(final String bucketName, final File file) {
       // final String uniqueFileName = LocalDateTime.now() + "_" + file.getName();
    	final String uniqueFileName = file.getName();
        final PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, uniqueFileName, file);
        amazonS3.putObject(putObjectRequest);
    }
    
    public void downloadFileFromS3(String fileName) throws IOException {
    	 S3Object fullObject = null;
	    try {	    	
	    	 fullObject = amazonS3.getObject(new GetObjectRequest(bucketName, fileName));
	         System.out.println("Content-Type: " + fullObject.getObjectMetadata().getContentType());
	         InputStream in = fullObject.getObjectContent();
	         BufferedImage imageFromAWS = ImageIO.read(in);
	         File outputfile = new File("C:\\AWSFile\\downloads" +
					 "_"+fileName);
	         ImageIO.write(imageFromAWS, "JPG", outputfile );	         
	    } catch (AmazonServiceException e) {
	        e.printStackTrace();
	    } catch (SdkClientException e) {
	        e.printStackTrace();
	    } finally {
	        if (fullObject != null) {
	            fullObject.close();
	        }
	    }
    }
    
    public void deleteFile(String fileName) {
    	System.out.println(bucketName +" "+fileName);
    	amazonS3.deleteObject(new DeleteObjectRequest(bucketName, fileName));
    	fileRepository.deleteByFileName(fileName);
    }

	public List viewAllFiles(User user) {
		List myFileList = new ArrayList<FileEntity>();
		fileRepository.findAll().forEach(myFileList::add);
		return myFileList;
	}
	
	public List viewMyFiles(User user) {
		List myFileList = new ArrayList<FileEntity>();		
		fileRepository.findByUserEmail(user.getUserEmail()).forEach(myFileList::add);
		return myFileList;
	}

	public FileEntity getMyUserFileDetails(Long fileId) {
		FileEntity entity = new FileEntity();
		entity = fileRepository.findByFileId(fileId);
		return entity;
	}

	public void updateFileAbout(FileEntity fileEntity) {
		System.out.println(fileEntity.getFileId());
		fileJPARepository.updateFileAbout(fileEntity.getFileId(), fileEntity.getUpdateDate(),
				fileEntity.getFileDesc(), fileEntity.getFileName(), fileEntity.getFileURL());		
	}

	public void updateFileDetail(FileEntity fileEntity) {
		fileJPARepository.updateFileDetail(fileEntity.getFileId(),fileEntity.getUpdateDate());
	}

	public List<FileEntity> getAllFiles() {
		List myFileList = new ArrayList<FileEntity>();
		fileRepository.findAll().forEach(myFileList::add);
		return myFileList;
	}
}

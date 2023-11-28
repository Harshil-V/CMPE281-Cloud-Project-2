package com.sjsu.cloud.travelapp.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.model.*;
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
import com.sjsu.cloud.travelapp.entity.FileTagEntity;
import com.sjsu.cloud.travelapp.entity.TagEntity;
import com.sjsu.cloud.travelapp.repository.FileJPARepository;
import com.sjsu.cloud.travelapp.repository.FileRepository;
import com.sjsu.cloud.travelapp.repository.FileTagRepository;
import com.sjsu.cloud.travelapp.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class FileService {
	@Autowired
	private AmazonS3 amazonS3;

	@Autowired
	private AmazonTextract amazonTextract;

	@Autowired
	private AmazonRekognition amazonRekognition;

	@Autowired
	FileRepository fileRepository;

	@Autowired
	FileJPARepository fileJPARepository;

	@Autowired
	TagRepository tagRepository;

	@Autowired
	FileTagRepository fileTagRepository;

	@Value("${aws.s3.bucket}")
	private String bucketName;

	@Value("${aws.cloudfront.distribution}")
	private String cloudFrontURL;

	@Async
	public ResponseEntity<?> uploadFile(final MultipartFile multipartFile, FileEntity fileEntity) {
		try {
			final File file = convertMultiPartFileToFile(multipartFile);
			String fileName = uploadFileToS3Bucket(bucketName, file);
			fileEntity.setFileName(fileName);
			fileEntity.setFileURL(cloudFrontURL+fileName);
			fileEntity.setUploadDate(String.valueOf(LocalDate.now()));
			file.delete();
			fileRepository.save(fileEntity);
			return analyzeImages(bucketName, fileName);
		} catch (final AmazonServiceException ex) {
			System.out.println("Error while uploading file." + ex.getMessage());
			return new ResponseEntity<>("An error occurred while uploading file.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Async
	public ResponseEntity<?> uploadTextractFile(final MultipartFile multipartFile) {
		List<String> analyzedText = new ArrayList<>();
		try {
			final File file = convertMultiPartFileToFile(multipartFile);
			analyzedText = analyzeTextractDocument(file);
			file.delete();
			return new ResponseEntity<>(analyzedText, HttpStatus.OK);
		} catch (final AmazonServiceException | FileNotFoundException ex) {
			System.out.println("Error while analyzing file." + ex.getMessage());
			return new ResponseEntity<>("An error occurred while analyzing file.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<?> downloadFileFromS3(String fileName) throws IOException {
		S3Object fullObject = null;
		try {
			fullObject = amazonS3.getObject(new GetObjectRequest(bucketName, fileName));
			System.out.println("Content-Type: " + fullObject.getObjectMetadata().getContentType());
			InputStream in = fullObject.getObjectContent();
			BufferedImage imageFromAWS = ImageIO.read(in);
			String localFileName = fileName.substring(fileName.lastIndexOf(":") + 1);
			File outputfile = new File("C:\\AWSFile\\" + localFileName);
			ImageIO.write(imageFromAWS, "JPG", outputfile);
			return new ResponseEntity<>("File downloaded successfully!", HttpStatus.OK);
		} catch (AmazonServiceException e) {
			e.printStackTrace();
			return new ResponseEntity<>("An error occurred while downloading file.", HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (SdkClientException e) {
			e.printStackTrace();
			return new ResponseEntity<>("An error occurred while downloading file.", HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			if (fullObject != null) {
				fullObject.close();
			}
		}
	}

	public ResponseEntity<?> deleteFile(String fileName) {
		try {
			System.out.println(bucketName + " " + fileName);
			amazonS3.deleteObject(new DeleteObjectRequest(bucketName, fileName));
			fileRepository.deleteByFileName(fileName);
			return new ResponseEntity<>("File deleted successfully!", HttpStatus.OK);
		} catch (AmazonServiceException e) {
			e.printStackTrace();
			return new ResponseEntity<>("An error occurred while deleting the file.", HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (SdkClientException e) {
			e.printStackTrace();
			return new ResponseEntity<>("An error occurred while deleting the file.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<?> getAllFiles() {
		try {
			List<FileEntity> myFileList = new ArrayList<>();
			fileRepository.findAll().forEach(myFileList::add);
			return new ResponseEntity<>(myFileList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("An error occurred while fetching all files.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<?> getUserFilesDetails(String userEmail) {
		try {
			return new ResponseEntity<>(fileRepository.findByUserEmail(userEmail), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("An error occurred while fetching user files.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<?> getFileDetailsById(String fileName) {
		try {
			return new ResponseEntity<>(fileRepository.findByFileName(fileName), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("An error occurred while fetching file details.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<?> updateFileDetails(FileEntity fileEntity) {
		try {
			System.out.println(fileEntity.getFileName());
			fileEntity.setUpdateDate(String.valueOf(LocalDate.now()));
			fileJPARepository.updateFileDetails(fileEntity.getFileName(), fileEntity.getFileDesc(),
					fileEntity.getFileURL(), fileEntity.getVersionNo(), fileEntity.getUpdateDate(), fileEntity.getUserEmail());
			return new ResponseEntity<>("File details updated successfully!", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("An error occurred while updating file details.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<?> updateFileDate(FileEntity fileEntity) {
		try {
			fileEntity.setUpdateDate(String.valueOf(LocalDate.now()));
			fileJPARepository.updateFileDate(fileEntity.getFileName(), fileEntity.getUpdateDate());
			return new ResponseEntity<>("File details updated successfully!", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("An error occurred while updating file details.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Supporting methods below


	public ResponseEntity<?> analyzeImages(final String bucketName, String fileName) {

		// Detect an Image Uploaded that contains inappropriate content
		DetectModerationLabelsResult imageAnalyzeResponse = amazonRekognition.detectModerationLabels(
				new DetectModerationLabelsRequest()
						.withImage(new com.amazonaws.services.rekognition.model.Image()
								.withS3Object(new com.amazonaws.services.rekognition.model.S3Object()
										.withBucket(bucketName)
										.withName(fileName))));

		if (!imageAnalyzeResponse.getModerationLabels().isEmpty()) {
			deleteFile(fileName);
			return new ResponseEntity<>("File has been deleted due to " +
					"inappropriate content uploaded!", HttpStatus.OK);
		}

		System.out.println("No inappropriate content found!");

		// Call Rekognition to detect labels in the image
		DetectLabelsResult labelResponse = amazonRekognition.detectLabels(
				new DetectLabelsRequest()
						.withImage(new com.amazonaws.services.rekognition.model.Image()
								.withS3Object(new com.amazonaws.services.rekognition.model.S3Object()
										.withBucket(bucketName)
										.withName(fileName))));

		// Extract and print the detected labels
		for (Label label : labelResponse.getLabels()) {
			String tagId = String.valueOf(LocalTime.now());
			TagEntity tagEntity = new TagEntity();
			tagEntity.setTagId(tagId);
			tagEntity.setTagName(label.getName());
			tagRepository.save(tagEntity);

			FileTagEntity fileTagEntity = new FileTagEntity();
			FileTagEntity.FileTagId fileTagId = new FileTagEntity.FileTagId();
			fileTagId.setFileName(fileName);
			fileTagId.setTagId(tagId);
			fileTagEntity.setFileTagId(fileTagId);
			fileTagRepository.save(fileTagEntity);
		}
		return new ResponseEntity<>("File has been uploaded successfully!", HttpStatus.OK);
	}
	
	private List<String> analyzeTextractDocument(File file) throws FileNotFoundException {
		ByteBuffer imageBytes;
		try (InputStream inputStream = new FileInputStream(file)) {
			imageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		List<String> analyzedTextList = new ArrayList<>();
		DetectDocumentTextRequest request = new DetectDocumentTextRequest()
				.withDocument(new Document().withBytes(imageBytes));

		DetectDocumentTextResult result = amazonTextract.detectDocumentText(request);
		System.out.println(result);

		result.getBlocks().forEach(block -> {
			if (block.getBlockType().equals("LINE")) {
				analyzedTextList.add(block.getText());
			}
		});
		return analyzedTextList;
	}

	private File convertMultiPartFileToFile(final MultipartFile multipartFile) {
		final File file = new File(multipartFile.getOriginalFilename());
		try (final FileOutputStream outputStream = new FileOutputStream(file)) {
			outputStream.write(multipartFile.getBytes());
		} catch (final IOException ex) {
			System.out.println("Error converting the multi-part file to file= " + ex.getMessage());
		}
		return file;
	}

	private String uploadFileToS3Bucket(final String bucketName, final File file) {
		final String uniqueFileName = LocalDateTime.now() + "_" + file.getName();
		final PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, uniqueFileName, file);
		amazonS3.putObject(putObjectRequest);
		return uniqueFileName;
	}
}

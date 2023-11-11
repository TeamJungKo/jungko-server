package com.jungko.jungko_server.cloud.aws.domain;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.jungko.jungko_server.cloud.aws.config.AwsConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.services.s3.AmazonS3Client;

@Component
@RequiredArgsConstructor
@Slf4j
public class AwsS3Manager implements ObjectResourceManager {

	private final AwsConfig awsConfig;

	private final AmazonS3Client amazonS3Client;

	private ObjectMetadata createObjectMetadata(MultipartFile multipartFile) {
		ObjectMetadata objectMetadata = new ObjectMetadata();

		objectMetadata.setContentType(multipartFile.getContentType());
		objectMetadata.setContentLength(multipartFile.getSize());
		return objectMetadata;
	}

	@Override
	public void upload(MultipartFile multipartFile, String key) {
		log.info("Called upload method key = {}", key);
		try {
			ObjectMetadata objectMetadata = createObjectMetadata(multipartFile);
			PutObjectRequest putObjectRequest = new PutObjectRequest(awsConfig.getBucket(), key,
					multipartFile.getInputStream(), objectMetadata);
			amazonS3Client.putObject(putObjectRequest);
		} catch (Exception e) {
			log.error("s3 upload error: {}", e.getMessage());
			throw new HttpClientErrorException(HttpStatus.BAD_GATEWAY);
		}
	}

	@Override
	public void delete(String key) {
		log.info("Called delete method key = {}", key);
		try {
			amazonS3Client.deleteObject(awsConfig.getBucket(), key);
		} catch (Exception e) {
			log.error("s3 delete error: {}", e.getMessage());
			throw new HttpClientErrorException(HttpStatus.BAD_GATEWAY);
		}
	}

	@Override
	public String getImageUrl(String key) {
		log.info("Called getImageUrl method key = {}", key);
		try {
			return amazonS3Client.getUrl(awsConfig.getBucket(), key).toString();
		} catch (Exception e) {
			log.error("s3 getImageUrl error: {}", e.getMessage());
			throw new HttpClientErrorException(HttpStatus.BAD_GATEWAY);
		}
	}

	@Override
	public boolean isImageExist(String key) {
		log.info("Called isImageExist method key = {}", key);
		try {
			return amazonS3Client.doesObjectExist(awsConfig.getBucket(), key);
		} catch (Exception e) {
			log.error("s3 isImageExist error: {}", e.getMessage());
			throw new HttpClientErrorException(HttpStatus.BAD_GATEWAY);
		}
	}
}

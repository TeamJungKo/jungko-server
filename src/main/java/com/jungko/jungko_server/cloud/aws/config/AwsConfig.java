package com.jungko.jungko_server.cloud.aws.config;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;

@Configuration
@Getter
public class AwsConfig {

	@Value("${cloud.aws.credentials.access-key}")
	private String accessKey;
	@Value("${cloud.aws.credentials.secret-key}")
	private String secretKey;
	@Value("${cloud.aws.region.static}")
	private String region;
	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	private AWSStaticCredentialsProvider createAwsCredentialsProvider() {
		BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(this.accessKey,
				this.secretKey);
		return new AWSStaticCredentialsProvider(basicAWSCredentials);
	}

	@Bean
	public AmazonS3Client amazonS3Client() {
		return (AmazonS3Client) AmazonS3Client.builder()
				.withRegion(region)
				.withCredentials(createAwsCredentialsProvider())
				.build();
	}
}

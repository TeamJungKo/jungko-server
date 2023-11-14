package com.jungko.jungko_server.cloud.aws.domain;

import org.springframework.web.multipart.MultipartFile;

public interface ObjectResourceManager {

	void upload(MultipartFile multipartFile, String key);

	void delete(String key);

	String getImageUrl(String key);

	boolean isImageExist(String key);
}

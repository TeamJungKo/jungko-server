package com.jungko.jungko_server.util;


import com.jungko.jungko_server.cloud.aws.domain.ObjectResourceManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ImageUtil {

	private final ObjectResourceManager objectResourceManager;

	public String uploadFile(MultipartFile multipartFile, String dirName) {
		String filePath = createFilePath(multipartFile.getOriginalFilename(), dirName);
		objectResourceManager.upload(multipartFile, filePath);
		return filePath;
	}

	private String createFilePath(String originalFilename, String dirName) {
		return dirName + UUID.randomUUID() + "_" + originalFilename;
	}

	public void deleteFile(String filePath) {
		objectResourceManager.delete(filePath);
	}

	public String getImageUrl(String key) {
		return objectResourceManager.getImageUrl(key);
	}

	public boolean isImageExist(String key) {
		return objectResourceManager.isImageExist(key);
	}
}

package com.jungko.jungko_server.utils.bean;

import com.jungko.jungko_server.cloud.aws.domain.ObjectResourceManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.MultipartFile;

@TestConfiguration
public class ExternalDependenciesIgnore {

	@Bean
	public ObjectResourceManager objectResourceManager() {
		return new ObjectResourceManager() {
			@Override
			public void upload(MultipartFile multipartFile, String key) {
			}

			@Override
			public void delete(String key) {
			}

			@Override
			public String getImageUrl(String key) {
				return null;
			}

			@Override
			public boolean isImageExist(String key) {
				return false;
			}
		};
	}
}

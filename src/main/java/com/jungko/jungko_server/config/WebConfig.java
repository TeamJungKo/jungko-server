package com.jungko.jungko_server.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

	private final ClientConfig clientConfig;

	@Override
	public void addCorsMappings(CorsRegistry registry) {

		registry.addMapping("/**")
				.allowedOrigins(clientConfig.getClientUrl(), clientConfig.getClientHttpUrl())
				.allowedMethods("*")
				.allowCredentials(true)
				.maxAge(3600)
				.allowedHeaders("*");
	}
}

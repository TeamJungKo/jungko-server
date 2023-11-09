package com.jungko.jungko_server.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class ClientConfig {

	@Value("${jungko.client.url}")
	private String clientUrl;

	@Value("${jungko.client.callback}")
	private String clientCallbackUrl;
}

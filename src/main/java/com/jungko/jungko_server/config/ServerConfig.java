package com.jungko.jungko_server.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class ServerConfig {

	@Value("${jungko.server.oauth2.login-endpoint}")
	private String oauth2LoginEndpoint;

	@Value("${jungko.server.oauth2.callback-endpoint}")
	private String oauth2CallbackEndpoint;
}

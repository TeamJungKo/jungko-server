package com.jungko.jungko_server.auth.jwt;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class JwtConfig {

	@Value("${jungko.jwt.roles-key}")
	private String rolesKey;

	@Value("${jungko.jwt.scopes-key}")
	private String scopesKey;

	@Value("${jungko.jwt.user-id-key}")
	private String userIdKey;

	@Value("${jungko.jwt.common-token-expire-time}")
	private long commonTokenExpireTime;

	@Value("${jungko.jwt.refresh-token-expire-time}")
	private long refreshTokenExpireTime;
}

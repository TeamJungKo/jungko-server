package com.jungko.jungko_server.auth.jwt;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class JwtConfig {

	public static final String ROLES = "roles";
	public static final String SCOPES = "scopes";
	public static final String USERID = "userId";

	@Value("${jungko.jwt.common-token-expire-time}")
	private long commonTokenExpireTime;

	@Value("${jungko.jwt.refresh-token-expire-time}")
	private long refreshTokenExpireTime;
}

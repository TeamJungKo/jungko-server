package com.jungko.jungko_server.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class CookieConfig {

	@Value("${jungko.cookie.domain}")
	private String domain;

	@Value("${jungko.cookie.name}")
	private String name;

	@Value("${jungko.cookie.path}")
	private String path;

	@Value("${jungko.cookie.max-age}")
	private int maxAge;

	@Value("${jungko.cookie.http-only}")
	private boolean httpOnly;

	@Value("${jungko.cookie.secure}")
	private boolean secure;

	@Value("${jungko.cookie.same-site}")
	private String sameSite;
}

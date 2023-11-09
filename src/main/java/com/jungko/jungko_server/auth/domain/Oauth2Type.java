package com.jungko.jungko_server.auth.domain;

public enum Oauth2Type {
	GOOGLE,
	NAVER,
	KAKAO,
	;

	public static Oauth2Type of(String name) {
		return Oauth2Type.valueOf(name.toUpperCase());
	}
}

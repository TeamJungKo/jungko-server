package com.jungko.jungko_server.auth.oauth2.extractor;

import com.jungko.jungko_server.auth.domain.Oauth2Type;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface Oauth2AttributeExtractor {

	String getEmail();

	String getNickname();

	String getProfileImageUrl();

	Oauth2Type getOauthType();

	String getOauthId();

	static Oauth2AttributeExtractor create(OAuth2User auth2User, String providerName) {
		switch (providerName) {
			case "google":
				return new GoogleOauth2AttributeExtractor(auth2User);
			case "kakao":
				return new KakaoOauth2AttributeExtractor(auth2User);
			case "naver":
				return new NaverOauth2AttributeExtractor(auth2User);
			default:
				throw new IllegalArgumentException("지원하지 않는 소셜 로그인입니다.");
		}
	}
}

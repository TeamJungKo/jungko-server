package com.jungko.jungko_server.auth.oauth2.extractor;

import com.jungko.jungko_server.auth.domain.Oauth2Type;
import java.util.Map;
import org.springframework.security.oauth2.core.user.OAuth2User;

/**
 * // @formatter:off
 * Oauth2User로부터 속성을 추출하는 메서드를 정의하는 인터페이스 Oauth 유저 정보를 추출하여 우리 시스템에 맞도록 변환하는 역할을 한다.
 * 소셜 인증 기관이 추가되면이 인터페이스를 구현하여 Oauth2User로부터 속성을 추출하는 메서드를 정의해야 한다.
 * // @formatter:on
 */
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

	static String getNestedAttribute(OAuth2User user, String... keys) {
		Object value = user.getAttributes();
		for (String key : keys) {
			if (!(value instanceof Map)) {
				return null;
			}
			value = ((Map<?, ?>) value).get(key);
		}
		return value instanceof String ? (String) value : null;
	}
}

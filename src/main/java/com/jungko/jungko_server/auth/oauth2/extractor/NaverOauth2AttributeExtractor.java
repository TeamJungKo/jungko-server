package com.jungko.jungko_server.auth.oauth2.extractor;

import com.jungko.jungko_server.auth.domain.Oauth2Type;
import lombok.ToString;
import org.springframework.security.oauth2.core.user.OAuth2User;

@ToString
public class NaverOauth2AttributeExtractor implements Oauth2AttributeExtractor {

	private final OAuth2User auth2User;

	public NaverOauth2AttributeExtractor(OAuth2User auth2User) {
		this.auth2User = auth2User;
	}

	@Override
	public String getEmail() {
		return Oauth2AttributeExtractor.getNestedAttribute(auth2User, "response", "email");
	}

	@Override
	public String getNickname() {
		return Oauth2AttributeExtractor.getNestedAttribute(auth2User, "response", "nickname");
	}

	@Override
	public String getProfileImageUrl() {
		return Oauth2AttributeExtractor.getNestedAttribute(auth2User, "response", "profile_image");
	}

	@Override
	public Oauth2Type getOauthType() {
		return Oauth2Type.NAVER;
	}

	@Override
	public String getOauthId() {
		return Oauth2AttributeExtractor.getNestedAttribute(auth2User, "response", "id");
	}
}

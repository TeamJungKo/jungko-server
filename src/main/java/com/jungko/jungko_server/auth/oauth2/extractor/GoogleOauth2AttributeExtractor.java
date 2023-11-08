package com.jungko.jungko_server.auth.oauth2.extractor;

import com.jungko.jungko_server.auth.domain.Oauth2Type;
import lombok.ToString;
import org.springframework.security.oauth2.core.user.OAuth2User;

@ToString
public class GoogleOauth2AttributeExtractor implements Oauth2AttributeExtractor {

	private final OAuth2User auth2User;

	public GoogleOauth2AttributeExtractor(OAuth2User auth2User) {
		this.auth2User = auth2User;
	}

	@Override
	public String getEmail() {
		return auth2User.getAttribute("email");
	}

	@Override
	public String getNickname() {
		return auth2User.getAttribute("name");
	}

	@Override
	public String getProfileImageUrl() {
		return auth2User.getAttribute("picture");
	}

	@Override
	public Oauth2Type getOauthType() {
		return Oauth2Type.GOOGLE;
	}

	@Override
	public String getOauthId() {
		return auth2User.getAttribute("sub");
	}
}

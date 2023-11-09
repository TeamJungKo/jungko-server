package com.jungko.jungko_server.auth.oauth2.extractor;

import com.jungko.jungko_server.auth.domain.Oauth2Type;
import java.util.Objects;
import lombok.ToString;
import org.springframework.security.oauth2.core.user.OAuth2User;

@ToString
public class KakaoOauth2AttributeExtractor implements Oauth2AttributeExtractor {

	private final OAuth2User auth2User;

	public KakaoOauth2AttributeExtractor(OAuth2User auth2User) {
		this.auth2User = auth2User;
	}

	@Override
	public String getEmail() {
		return Oauth2AttributeExtractor.getNestedAttribute(auth2User, "kakao_account",
				"email");
	}

	@Override
	public String getNickname() {
		return Oauth2AttributeExtractor.getNestedAttribute(auth2User, "properties", "nickname");
	}

	@Override
	public String getProfileImageUrl() {
		return Oauth2AttributeExtractor.getNestedAttribute(auth2User, "properties",
				"profile_image");
	}

	@Override
	public Oauth2Type getOauthType() {
		return Oauth2Type.KAKAO;
	}

	@Override
	public String getOauthId() {
		return Objects.requireNonNull(auth2User.getAttribute("id")).toString();
	}
}

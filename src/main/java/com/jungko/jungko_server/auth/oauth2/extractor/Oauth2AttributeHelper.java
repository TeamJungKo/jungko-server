package com.jungko.jungko_server.auth.oauth2.extractor;

import java.util.Arrays;
import java.util.Map;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class Oauth2AttributeHelper {


	private final OAuth2User auth2User;

	protected Oauth2AttributeHelper(OAuth2User auth2User) {
		this.auth2User = auth2User;
	}

	public static Oauth2AttributeHelper of(OAuth2User auth2User) {
		return new Oauth2AttributeHelper(auth2User);
	}

	public Map<String, Object> getAuthAttributes() {
		return auth2User.getAttributes();
	}

	public <T> T getAttribute(String... name) {
		return getMap(getAuthAttributes(), name);
	}

	public <T> T getAttribute(String name) {
		return getMap(getAuthAttributes(), name);
	}

	@SuppressWarnings("unchecked")
	private <T> T getMap(Map<String, Object> map, String... names) {
		if (names.length == 0) {
			throw new IllegalArgumentException("names is empty");
		}
		try {
			return names.length == 1 ?
					(T) map.get(names[0])
					: getMap((Map<String, Object>) map.get(names[0]),
							Arrays.copyOfRange(names, 1, names.length));
		} catch (Exception e) {
			throw new IllegalArgumentException("names are invalid");
		}

	}
}

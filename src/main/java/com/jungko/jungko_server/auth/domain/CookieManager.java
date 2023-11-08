package com.jungko.jungko_server.auth.domain;

import com.jungko.jungko_server.config.CookieConfig;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CookieManager {

	private final CookieConfig cookieConfig;

	public void createCookie(HttpServletResponse res, String token) {
		log.info("Called setCookie path");
		Cookie cookie = new Cookie(cookieConfig.getName(), token);
		cookie.setPath(cookieConfig.getPath());
		cookie.setMaxAge(cookieConfig.getMaxAge());
		cookie.setHttpOnly(cookieConfig.isHttpOnly());
		cookie.setDomain(cookieConfig.getDomain());
		res.addCookie(cookie);
	}
}

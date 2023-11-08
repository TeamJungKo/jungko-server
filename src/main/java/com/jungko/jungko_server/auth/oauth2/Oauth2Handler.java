package com.jungko.jungko_server.auth.oauth2;

import com.jungko.jungko_server.auth.domain.CookieManager;
import com.jungko.jungko_server.auth.jwt.JwtTokenProvider;
import com.jungko.jungko_server.auth.oauth2.extractor.Oauth2AttributeExtractor;
import com.jungko.jungko_server.config.ClientConfig;
import com.jungko.jungko_server.member.domain.Member;
import com.jungko.jungko_server.member.infrastructure.MemberRepository;
import com.jungko.jungko_server.util.AuthResponseMessages;
import java.time.LocalDateTime;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class Oauth2Handler {

	private final MemberRepository memberRepository;
	private final JwtTokenProvider jwtTokenProvider;
	private final CookieManager cookieManager;
	private final ClientConfig clientConfig;

	@Bean
	public AuthenticationSuccessHandler oauth2AuthenticationSuccessHandler() {
		return (request, response, authentication) -> {
			DefaultOAuth2User auth2User = (DefaultOAuth2User) authentication.getPrincipal();
			OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
			String providerName = token.getAuthorizedClientRegistrationId();
			Oauth2AttributeExtractor extractor = Oauth2AttributeExtractor.create(
					auth2User, providerName
			);
			Member member = memberRepository.findByEmail(extractor.getEmail())
					.orElseGet(() ->
							memberRepository.save(
									Member.createMember(
											extractor.getEmail(),
											extractor.getProfileImageUrl(),
											extractor.getNickname(),
											false,
											extractor.getOauthType(),
											extractor.getOauthId(),
											LocalDateTime.now()
									)
							)
					);
			log.info("Member Login Success: {}", member);
			String jwtToken = jwtTokenProvider.createCommonAccessToken(member.getId())
					.getTokenValue();
			cookieManager.createCookie(
					response, jwtToken
			);
			response.sendRedirect(
					clientConfig.getClientUrl() + clientConfig.getClientCallbackUrl());
		};
	}

	@Bean
	public AuthenticationFailureHandler oauth2AuthenticationFailureHandler() {
		return (request, response, exception) -> {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().println(AuthResponseMessages.OAUTH2_FAILURE);
		};
	}
}
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
/*
  Oauth2 인증에 대한 핸들러를 정의하는 클래스
 */
public class Oauth2Handler {

	private final MemberRepository memberRepository;
	private final JwtTokenProvider jwtTokenProvider;
	private final CookieManager cookieManager;
	private final ClientConfig clientConfig;

	@Bean
	/*
	  Oauth2 인증 성공 시 호출되는 핸들러
	  인증 정보를 우리 시스템에 맞도록 변환한다.
	  변환된 인증 정보를 Member 엔티티로 영속화하고, 회원의 ID를 이용해 JWT 토큰을 생성한다.
	  생성된 JWT 토큰을 쿠키에 담아 클라이언트로 전송한다.
	 */
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
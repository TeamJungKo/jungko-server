package com.jungko.jungko_server.auth.jwt;

import com.jungko.jungko_server.auth.domain.MemberRole;
import com.jungko.jungko_server.auth.dto.MemberSessionDto;
import com.jungko.jungko_server.util.JwtAuthenticationTokenException;
import java.io.IOException;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.Transient;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

@Transient
@Slf4j
@RequiredArgsConstructor
/*
  JWT 토큰을 이용한 인증을 위한 커스텀 인증 필터
 */
public class MemberSessionAuthenticationFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		System.out.println(request.getRequestURI());
		if (request.getRequestURI().contains("swagger")
				|| request.getRequestURI().contains("api-docs")
		) {
			filterChain.doFilter(request, response);
			return;
		}
		if (authentication == null) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인이 필요합니다.");
			return;
		}
		try {
			if (authentication instanceof JwtAuthenticationToken) {
				JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
				MemberSessionAuthenticationToken token = convert(jwtAuthenticationToken);
				context.setAuthentication(token);
				SecurityContextHolder.clearContext();
				SecurityContextHolder.setContext(context);
			}
		} catch (JwtAuthenticationTokenException e) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
			return;
		}
		doFilter(request, response, filterChain);
	}

	/**
	 * JwtAuthenticationToken을 우리 시스템의 커스텀 MemberSessionAuthenticationToken으로 변환한다.
	 *
	 * @param token JwtAuthenticationToken 인증 토큰
	 * @return 변환된 커스텀 인증 토큰
	 */
	private MemberSessionAuthenticationToken convert(JwtAuthenticationToken token) {
		Object target = token.getPrincipal();
		if (target instanceof Jwt) {
			Jwt jwt = (Jwt) target;
			MemberSessionDto principal = convertPrincipal(jwt);
			MemberSessionAuthenticationToken ret = new MemberSessionAuthenticationToken(principal,
					token.getAuthorities());
			ret.setAuthenticated(true);
			log.info("convert token principal: {}, authorities: {}", ret.getPrincipal(),
					token.getAuthorities());
			return ret;
		}
		throw new JwtAuthenticationTokenException("JwtAuthenticationToken의 Principal이 Jwt가 아닙니다.");
	}

	/**
	 * Jwt Principal 정보를 토대로 회원 인증 정보 DTO 객체를 생성한다.
	 *
	 * @param jwt Jwt Principal 정보
	 * @return 회원 인증 정보 DTO 객체
	 */
	private MemberSessionDto convertPrincipal(Jwt jwt) {
		Long memberId = jwt.getClaim(JwtConfig.USERID);
		List<MemberRole> roles = jwt.getClaim(JwtConfig.ROLES);
		log.debug("memberId: {}, roles: {}", memberId, roles);
		if (memberId == null || roles == null) {
			throw new JwtAuthenticationTokenException("Jwt에 필요한 정보가 없습니다.");
		}
		return MemberSessionDto.builder()
				.memberId(memberId)
				.roles(roles)
				.build();
	}
}
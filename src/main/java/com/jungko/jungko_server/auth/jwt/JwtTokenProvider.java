package com.jungko.jungko_server.auth.jwt;

import com.jungko.jungko_server.auth.domain.MemberRole;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

	private final JwtEncoder jwtEncoder;
	private final JwtConfig jwtConfig;

	public Jwt createCommonAccessToken(Long id) {
		Instant now = Instant.now();
		JwsHeader header = JwsHeader
				.with(MacAlgorithm.HS256)
				.build();

		JwtClaimsSet claims = JwtClaimsSet.builder()
				.issuedAt(now)
				.expiresAt(now.plusSeconds(jwtConfig.getCommonTokenExpireTime()))
				.claim(jwtConfig.getUserIdKey(), id)
				.claim(jwtConfig.getRolesKey(), List.of(MemberRole.COMMON.name()))
				.build();
		return jwtEncoder.encode(JwtEncoderParameters.from(header, claims));
	}
}

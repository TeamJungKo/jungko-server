package com.jungko.jungko_server.auth.controller;

import com.jungko.jungko_server.auth.annotation.LoginMemberInfo;
import com.jungko.jungko_server.auth.domain.MemberRole;
import com.jungko.jungko_server.auth.dto.MemberSessionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/auth")
@Slf4j
@Tag(name = "인증", description = "인증 관련 API")
public class AuthController {

	@Operation(summary = "회원 탈퇴", description = "회원 탈퇴를 요청합니다. 회원과 관련된 모든 정보(작성한 카드, 관심 카드, 관심 키워드, 회원 정보 등)가 삭제되며, 소셜 인증 기관에 revoke 요청을 수행합니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "no content"),
	})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping(value = "/unregister")
	@Secured(MemberRole.S_USER)
	public void unregister(@LoginMemberInfo MemberSessionDto memberSessionDto) {
		log.info("Called unregister member: {}", memberSessionDto);
	}
}

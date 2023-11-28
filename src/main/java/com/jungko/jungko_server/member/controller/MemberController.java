package com.jungko.jungko_server.member.controller;

import com.jungko.jungko_server.auth.annotation.LoginMemberInfo;
import com.jungko.jungko_server.auth.domain.MemberRole;
import com.jungko.jungko_server.auth.dto.MemberSessionDto;
import com.jungko.jungko_server.mapper.MemberMapper;
import com.jungko.jungko_server.member.dto.MemberProfileDto;
import com.jungko.jungko_server.member.dto.request.MemberProfileUpdateRequestDto;
import com.jungko.jungko_server.member.dto.response.MemberProfileResponseDto;
import com.jungko.jungko_server.member.dto.response.MyProfileResponseDto;
import com.jungko.jungko_server.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import javax.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/members")
@Slf4j
@Tag(name = "회원", description = "회원 관련 API")
public class MemberController {

	private final MemberService memberService;
	private final MemberMapper memberMapper;

	@Operation(summary = "내 프로필 조회", description = "내 프로필을 조회합니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "ok"),
	})
	@GetMapping(value = "/me/profile")
	@Secured(MemberRole.S_USER)
	public MyProfileResponseDto getMyProfile(
			@LoginMemberInfo MemberSessionDto memberSessionDto
	) {
		log.info("Called getMyProfile member: {}", memberSessionDto);

		return memberService.getMyProfile(
				memberSessionDto.getMemberId());
	}

	@Operation(summary = "내 프로필 수정", description = "내 프로필 정보를 수정합니다. null이 아닌 필드에 대해서만 수정이 이루어집니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "ok"),
	})
	@PatchMapping(value = "/me/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@Secured(MemberRole.S_USER)
	public MemberProfileResponseDto updateMyProfile(
			@LoginMemberInfo MemberSessionDto memberSessionDto,
			@Valid @ModelAttribute MemberProfileUpdateRequestDto dto
	) {
		log.info("Called updateCard member: {}, dto: {}", memberSessionDto, dto);
		MemberProfileDto memberProfileDto = memberService.updateMemberProfile(
				memberSessionDto.getMemberId(), dto);
		return memberMapper.toMemberProfileResponseDto(memberProfileDto);
	}

	@Operation(summary = "회원 프로필 조회", description = "특정 회원의 프로필을 조회합니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "ok"),
	})
	@GetMapping(value = "/{memberId}/profile")
	@Secured(MemberRole.S_USER)
	public MemberProfileResponseDto getMemberProfile(
			@LoginMemberInfo MemberSessionDto memberSessionDto,
			@PathVariable("memberId") Long memberId) {
		log.info("Called getMemberProfile member: {}, memberId: {}", memberSessionDto, memberId);
		MemberProfileDto memberProfileDto = memberService.getMemberProfile(memberId);
		return memberMapper.toMemberProfileResponseDto(memberProfileDto);
	}
}

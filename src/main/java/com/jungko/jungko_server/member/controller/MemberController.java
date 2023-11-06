package com.jungko.jungko_server.member.controller;

import com.jungko.jungko_server.member.dto.request.MemberProfileUpdateRequestDto;
import com.jungko.jungko_server.member.dto.response.MemberProfileResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import javax.validation.Valid;
import org.springframework.http.MediaType;
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

    @Operation(summary = "내 프로필 조회", description = "내 프로필을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok"),
    })
    @GetMapping(value = "/me/profile")
    public MemberProfileResponseDto getMyProfile() {
        log.info("Called getMyProfile");
        return MemberProfileResponseDto.builder().build();
    }

    @Operation(summary = "내 프로필 수정", description = "내 프로필 정보를 수정합니다. null이 아닌 필드에 대해서만 수정이 이루어집니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok"),
    })
    @PatchMapping(value = "/me/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void updateCard(
            @Valid @ModelAttribute MemberProfileUpdateRequestDto dto,
            @PathVariable("memberId") Long memberId) {
        log.info("Called updateCard dto: {}", dto);
    }

    @Operation(summary = "회원 프로필 조회", description = "특정 회원의 프로필을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok"),
    })
    @GetMapping(value = "/{memberId}/profile")
    public MemberProfileResponseDto getMemberProfile(
            @PathVariable("memberId") Long memberId) {
        log.info("Called getMemberProfile memberId: {}", memberId);
        return MemberProfileResponseDto.builder().build();
    }
}

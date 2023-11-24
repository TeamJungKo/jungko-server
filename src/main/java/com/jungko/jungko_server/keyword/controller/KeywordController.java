package com.jungko.jungko_server.keyword.controller;

import com.jungko.jungko_server.auth.annotation.LoginMemberInfo;
import com.jungko.jungko_server.auth.domain.MemberRole;
import com.jungko.jungko_server.auth.dto.MemberSessionDto;
import com.jungko.jungko_server.keyword.dto.request.KeywordRequestDto;
import com.jungko.jungko_server.keyword.dto.response.KeywordListResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/keywords")
@Slf4j
@Tag(name = "키워드", description = "키워드 관련 API")
public class KeywordController {

	@Operation(summary = "키워드 생성", description = "새 키워드들을 생성합니다. 동일한 키워드로 여러 번 요청이 들어오면 하나만 생성됩니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "created"),
			@ApiResponse(responseCode = "500", description = "internal server error")
	})
	@PutMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	@Secured(MemberRole.S_USER)
	public void createKeyword(
			@LoginMemberInfo MemberSessionDto memberSessionDto,
			@Valid @ModelAttribute KeywordRequestDto dto) {
		log.info("Called createKeyword member: {}, dto: {}", memberSessionDto, dto);
	}

	@Operation(summary = "키워드 삭제", description = "특정 키워드를 삭제합니다. 키워드 ID를 배열에 담아 ID에 해당하는 키워드들을 삭제합니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "no content"),
			@ApiResponse(responseCode = "500", description = "internal server error")
	})
	@DeleteMapping(value = "/{keywordId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Secured(MemberRole.S_USER)
	public void deleteCard(
			@LoginMemberInfo MemberSessionDto memberSessionDto,
			@PathVariable("keywordId") Long keywordId) {
		log.info("Called deleteKeyword member: {}, keywordId: {}", memberSessionDto, keywordId);
	}

	@Operation(summary = "내가 등록한 키워드 목록 조회", description = "내가 등록한 키워드 목록을 조회합니다. 키워드 개수는 최대 50개로 제한됩니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "ok"),
	})
	@GetMapping(value = "/members/me")
	@Secured(MemberRole.S_USER)
	public KeywordListResponseDto getMyKeywords(
			@LoginMemberInfo MemberSessionDto memberSessionDto,
			@RequestParam Integer page,
			@RequestParam Integer size) {
		log.info("Called getMyKeywords member: {}, page: {}, size: {}", memberSessionDto, page,
				size);

		return KeywordListResponseDto.builder().build();
	}

	@Operation(summary = "특정 회원 키워드 목록 조회", description = "memberId에 해당하는 회원이 등록한 키워드 목록을 조회합니다. 키워드 개수는 최대 50개로 제한됩니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "ok"),
	})
	@GetMapping(value = "/members/{memberId}")
	@Secured(MemberRole.S_USER)
	public KeywordListResponseDto getMemberKeywords(
			@LoginMemberInfo MemberSessionDto memberSessionDto,
			@PathVariable("memberId") Long memberId) {
		log.info("Called getMemberKeywords member: {}, memberId: {}", memberSessionDto, memberId);

		return KeywordListResponseDto.builder().build();
	}
}

package com.jungko.jungko_server.notification.controller;

import com.jungko.jungko_server.auth.annotation.LoginMemberInfo;
import com.jungko.jungko_server.auth.domain.MemberRole;
import com.jungko.jungko_server.auth.dto.MemberSessionDto;
import com.jungko.jungko_server.notification.dto.response.CardNoticeListResponseDto;
import com.jungko.jungko_server.notification.dto.response.KeywordNoticeListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import javax.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/notices")
@Slf4j
@Tag(name = "알림", description = "알림 관련 API")
public class NotificationController {

	@Operation(summary = "전체 카드 알림 조회", description = "전체 카드 알림을 조회합니다. 페이지네이션을 지원합니다. 알림은 최근 2주일까지만 제공됩니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "ok"),
			@ApiResponse(responseCode = "500", description = "internal server error")
	})
	@GetMapping(value = "/cards")
	@Secured(MemberRole.S_USER)
	public CardNoticeListResponseDto getCardNotice(
			@LoginMemberInfo MemberSessionDto memberSessionDto,
			@RequestParam Integer page,
			@RequestParam Integer size) {
		log.info("Called getCardNotice member: {}, page: {}, size: {}", memberSessionDto, page,
				size);

		return CardNoticeListResponseDto.builder().build();
	}

	@Operation(summary = "전체 키워드 알림 조회", description = "전체 키워드 알림을 조회합니다. 페이지네이션을 지원합니다. 알림은 최근 2주일까지만 제공됩니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "ok"),
			@ApiResponse(responseCode = "500", description = "internal server error")
	})
	@GetMapping(value = "/keywords")
	@Secured(MemberRole.S_USER)
	public KeywordNoticeListResponseDto getKeywordNotice(
			@LoginMemberInfo MemberSessionDto memberSessionDto,
			@RequestParam Integer page,
			@RequestParam Integer size) {
		log.info("Called getKeywordNotice member: {}, page: {}, size: {}", memberSessionDto, page,
				size);

		return KeywordNoticeListResponseDto.builder().build();
	}

	@Operation(summary = "전체 카드 알림 삭제", description = "전체 카드 알림을 삭제합니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "no content"),
			@ApiResponse(responseCode = "500", description = "internal server error")
	})
	@DeleteMapping(value = "/cards")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Secured(MemberRole.S_USER)
	public void deleteCardNotice(
			@LoginMemberInfo MemberSessionDto memberSessionDto
	) {
		log.info("Called deleteNotice member: {}", memberSessionDto);
	}

	@Operation(summary = "전체 키워드 알림 삭제", description = "전체 키워드 알림을 삭제합니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "no content"),
			@ApiResponse(responseCode = "500", description = "internal server error")
	})
	@DeleteMapping(value = "/keywords")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Secured(MemberRole.S_USER)
	public void deleteKeywordNotice(
			@LoginMemberInfo MemberSessionDto memberSessionDto
	) {
		log.info("Called deleteNotice member: {}", memberSessionDto);
	}

	@Operation(summary = "특정 알림 삭제", description = "특정 알림을 삭제합니다. 알림 ID를 배열에 담아 ID에 해당하는 알림들을 삭제합니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "no content"),
			@ApiResponse(responseCode = "500", description = "internal server error")
	})
	@DeleteMapping(value = "/")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Secured(MemberRole.S_USER)
	public void deleteNotice(
			@LoginMemberInfo MemberSessionDto memberSessionDto,
			@Valid @ModelAttribute List<Long> noticeIds) {
		log.info("Called deleteNotice member: {}, noticeIds: {}", memberSessionDto, noticeIds);
	}

	@Operation(summary = "특정 카드 알림 ON/OFF", description = "특정 카드에 대해 알림을 ON/OFF 합니다. 토글 API입니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "ok"),
			@ApiResponse(responseCode = "500", description = "internal server error")
	})
	@PutMapping(value = "/cards/{cardId}")
	@Secured(MemberRole.S_USER)
	public void cardNoticeToggle(
			@PathVariable("cardId") Long cardId) {
		log.info("Called cardNoticeToggle cardId: {}", cardId);
	}

	@Operation(summary = "특정 키워드 알림 ON/OFF", description = "특정 키워드에 대한 알림을 ON/OFF 합니다. 토글 API입니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "ok"),
			@ApiResponse(responseCode = "500", description = "internal server error")
	})
	@PutMapping(value = "/keywords/{keywordId}")
	@Secured(MemberRole.S_USER)
	public void keywordNoticeToggle(
			@LoginMemberInfo MemberSessionDto memberSessionDto,
			@PathVariable("keywordId") Long keywordId) {
		log.info("Called keywordNoticeToggle member: {}, keywordId: {}", memberSessionDto,
				keywordId);
	}

	@Operation(summary = "전체 알림 ON/OFF", description = "회원의 전체 알림 설정을 조작합니다. memberId를 통해 해당 member의 notification_agreement의 bool값을 변경합니다")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "ok"),
			@ApiResponse(responseCode = "500", description = "internal server error")
	})
	@PutMapping(value = "/setting")
	@Secured(MemberRole.S_USER)
	public void totalNoticeToggle(
			@LoginMemberInfo MemberSessionDto memberSessionDto
	) {
		log.info("Called totalNoticeToggle member: {}", memberSessionDto);
	}
}

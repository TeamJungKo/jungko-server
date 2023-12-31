package com.jungko.jungko_server.card.controller;

import com.jungko.jungko_server.auth.annotation.LoginMemberInfo;
import com.jungko.jungko_server.auth.domain.MemberRole;
import com.jungko.jungko_server.auth.dto.MemberSessionDto;
import com.jungko.jungko_server.card.domain.CardSortType;
import com.jungko.jungko_server.card.dto.CardPreviewDto;
import com.jungko.jungko_server.card.dto.request.CardCreateRequestDto;
import com.jungko.jungko_server.card.dto.request.CardUpdateRequestDto;
import com.jungko.jungko_server.card.dto.response.CardListResponseDto;

import com.jungko.jungko_server.card.dto.response.CardSearchProductListResponseDto;
import com.jungko.jungko_server.card.service.CardService;
import com.jungko.jungko_server.product.domain.ProductSortType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/cards")
@Slf4j
@Tag(name = "카드", description = "카드 관련 API")
public class CardController {

	private final CardService cardService;

	@Operation(summary = "카드 생성", description = "새 카드를 생성합니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "created"),
	})
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	@Secured(MemberRole.S_USER)
	public CardPreviewDto createCard(
			@LoginMemberInfo MemberSessionDto memberSessionDto,
			@Valid @ModelAttribute CardCreateRequestDto dto) {
		log.info("Called createCard member: {}, dto: {}", memberSessionDto, dto);

		return cardService.createCard(memberSessionDto.getMemberId(), dto);
	}

	@Operation(summary = "카드 삭제", description = "특정 카드를 삭제합니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "no content"),
	})
	@DeleteMapping(value = "/{cardId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Secured(MemberRole.S_USER)
	public void deleteCard(
			@LoginMemberInfo MemberSessionDto memberSessionDto,
			@PathVariable("cardId") Long cardId) {
		log.info("Called deleteCard member: {}, cardId: {}", memberSessionDto, cardId);

		cardService.deleteCard(memberSessionDto.getMemberId(), cardId);
	}

	@Operation(summary = "카드 옵션 변경", description = "카드 옵션을 변경합니다. null이 아닌 값들만 변경합니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "ok"),
	})
	@PatchMapping(value = "/{cardId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@Secured(MemberRole.S_USER)
	public void updateCard(
			@LoginMemberInfo MemberSessionDto memberSessionDto,
			@Valid @ModelAttribute CardUpdateRequestDto dto,
			@PathVariable("cardId") Long cardId) {
		log.info("Called updateCard member: {}, dto: {}, cardId: {}", memberSessionDto, dto,
				cardId);

		cardService.updateCard(memberSessionDto.getMemberId(), dto, cardId);
	}

	@Operation(summary = "내 카드 목록 조회", description = "내가 만든 카드 목록을 조회합니다. 페이지네이션을 지원합니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "ok"),
	})
	@GetMapping(value = "/members/me")
	@Secured(MemberRole.S_USER)
	public CardListResponseDto getMyCards(
			@LoginMemberInfo MemberSessionDto memberSessionDto,
			@RequestParam(defaultValue = "0") Integer page,
			@RequestParam(defaultValue = "10") Integer size,
			@RequestParam(required = false) Long categoryId,
			@RequestParam(required = false) CardSortType sort,
			@RequestParam(required = false) Direction order
	) {
		log.info("Called getMyCards member: {}, page: {}, size: {}, categoryId: {}",
				memberSessionDto, page, size, categoryId);

		PageRequest pageRequest;
		if (sort == null || order == null) {
			pageRequest = PageRequest.of(page, size);
		} else {
			pageRequest = PageRequest.of(page, size, order, sort.toString());
		}
		return cardService.getCardsByMemberId(memberSessionDto.getMemberId(),
				pageRequest, categoryId);
	}

	@Operation(summary = "특정 유저 카드 목록 조회", description = "특정 유저가 만든 카드 목록을 조회합니다. 페이지네이션을 지원합니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "ok"),
	})
	@GetMapping(value = "/members/{memberId}")
	@Secured(MemberRole.S_USER)
	public CardListResponseDto getMemberCards(
			@LoginMemberInfo MemberSessionDto memberSessionDto,
			@PathVariable("memberId") Long memberId,
			@RequestParam(defaultValue = "0") Integer page,
			@RequestParam(defaultValue = "10") Integer size,
			@RequestParam(required = false) Long categoryId,
			@RequestParam(required = false) CardSortType sort,
			@RequestParam(required = false) Direction order
	) {
		log.info(
				"Called getMemberCards member: {}, memberId: {}, page: {}, size: {}, categoryId: {}",
				memberSessionDto, memberId, page, size, categoryId);

		PageRequest pageRequest;
		if (sort == null || order == null) {
			pageRequest = PageRequest.of(page, size);
		} else {
			pageRequest = PageRequest.of(page, size, order, sort.toString());
		}
		return cardService.getCardsByMemberId(memberId, pageRequest, categoryId);
	}

	@Operation(summary = "인기 카드 목록 조회", description = "인기 카드 목록을 조회합니다. 페이지네이션을 지원합니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "ok"),
	})
	@GetMapping(value = "/popular")
	public CardListResponseDto getPopularCards(
			@RequestParam(defaultValue = "0") Integer page,
			@RequestParam(defaultValue = "10") Integer size,
			@RequestParam(required = false) Long categoryId,
			@RequestParam(required = false) CardSortType sort,
			@RequestParam(required = false) Direction order
	) {
		log.info("Called getPopularCards page: {}, size: {}, categoryId: {}",
				page, size, categoryId);

		PageRequest pageRequest;
		if (sort == null || order == null) {
			pageRequest = PageRequest.of(page, size);
		} else {
			pageRequest = PageRequest.of(page, size, order, sort.toString());
		}
		return cardService.getPopularCards(pageRequest, categoryId);
	}

	@Operation(summary = "카드 내 매물 검색", description = "카드 내 매물을 검색합니다. 페이지네이션을 지원합니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "ok"),
	})
	@GetMapping(value = "/{cardId}/products")
	public CardSearchProductListResponseDto searchProductsByCard(
			@LoginMemberInfo MemberSessionDto memberSessionDto,
			@PathVariable("cardId") Long cardId,
			@RequestParam(defaultValue = "0") Integer page,
			@RequestParam(defaultValue = "10") Integer size,
			@RequestParam(required = false) ProductSortType sort,
			@RequestParam(required = false) Direction order
	) {
		log.info(
				"Called searchProductsByCard member: {}, cardId: {}, page: {}, size: {}, sort: {}, order: {}",
				memberSessionDto, cardId, page, size, sort, order);

		PageRequest pageRequest;
		if (order == null) {
			pageRequest = PageRequest.of(page, size);
		} else {
			pageRequest = PageRequest.of(page, size, order, sort.toString());
		}
		return cardService.searchProductsByCard(memberSessionDto.getMemberId(), cardId,
				pageRequest);
	}
}

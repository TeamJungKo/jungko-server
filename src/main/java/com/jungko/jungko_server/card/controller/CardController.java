package com.jungko.jungko_server.card.controller;

import com.jungko.jungko_server.card.dto.request.CardCreateRequestDto;
import com.jungko.jungko_server.card.dto.request.CardUpdateRequestDto;
import com.jungko.jungko_server.card.dto.response.CardListResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @Operation(summary = "카드 생성", description = "새 카드를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "created"),
    })
    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void createCard(
            @Valid @ModelAttribute CardCreateRequestDto dto) {
        log.info("Called createCard dto: {}", dto);
    }

    @Operation(summary = "카드 삭제", description = "특정 카드를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "no content"),
    })
    @DeleteMapping(value = "/{cardId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCard(
            @PathVariable("cardId") Long cardId) {
        log.info("Called deleteCard {}", cardId);
    }

    @Operation(summary = "카드 옵션 변경", description = "카드 옵션을 변경합니다. null이 아닌 값들만 변경합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok"),
    })
    @PatchMapping(value = "/{cardId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void updateCard(
            @Valid @ModelAttribute CardUpdateRequestDto dto,
            @PathVariable("cardId") Long cardId) {
        log.info("Called updateCard dto: {}, cardId: {}", dto, cardId);
    }

    @Operation(summary = "내 카드 목록 조회", description = "내가 만든 카드 목록을 조회합니다. 페이지네이션을 지원합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok"),
    })
    @GetMapping(value = "/members/me")
    public CardListResponseDto getMyCards(
            @RequestParam Integer page,
            @RequestParam Integer size) {
        log.info("Called getMyCards page: {}, size: {}", page, size);

        return CardListResponseDto.builder().build();
    }

    @Operation(summary = "특정 유저 카드 목록 조회", description = "특정 유저가 만든 카드 목록을 조회합니다. 페이지네이션을 지원합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok"),
    })
    @GetMapping(value = "/members/{memberId}")
    public CardListResponseDto getMemberCards(
            @PathVariable("memberId") Long memberId,
            @RequestParam Integer page,
            @RequestParam Integer size) {
        log.info("Called getMemberCards memberId: {}, page: {}, size: {}", memberId, page, size);

        return CardListResponseDto.builder().build();
    }

    @Operation(summary = "인기 카드 목록 조회", description = "인기 카드 목록을 조회합니다. 페이지네이션을 지원합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok"),
    })
    @GetMapping(value = "/popular")
    public CardListResponseDto getPopularCards(
            @RequestParam Integer page,
            @RequestParam Integer size) {
        log.info("Called getPopularCards {}", page, size);

        return CardListResponseDto.builder().build();
    }
}

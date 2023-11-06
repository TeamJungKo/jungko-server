package com.jungko.jungko_server.card.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/cards")
@Slf4j
@Tag(name = "관심 카드", description = "관심 카드 관련 API")
public class InterestedCardController {

  @Operation(summary = "관심 카드 등록", description = "특정 카드를 관심 카드로 등록합니다. 자신이 만든 카드는 등록할 수 없습니다. 이미 등록한 관심 카드를 재등록 시, 성공 응답을 주며 아무런 변화가 일어나지 않습니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "ok"),
  })
  @PutMapping(value = "/{cardId}/like")
  @ResponseStatus(HttpStatus.OK)
  public void likeCard(
      @PathVariable("cardId") Long cardId) {
    log.info("Called likeCard {}", cardId);
  }

  @Operation(summary = "관심 카드 삭제", description = "특정 카드를 관심 카드에서 삭제합니다. 존재하지 않는 카드를 요청하면 404 실패 응답을 제공합니다.")

  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "no content"),
      @ApiResponse(responseCode = "404", description = "not found")
  })
  @DeleteMapping(value = "/{cardId}/like")
  @ResponseStatus(HttpStatus.OK)
  public void unlikeCard(
      @PathVariable("cardId") Long cardId) {
    log.info("Called unlikeCard {}", cardId);
  }
}

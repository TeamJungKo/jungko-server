package com.jungko.jungko_server.card.dto;

import java.time.LocalDateTime;

import com.jungko.jungko_server.area.dto.AreaDto;
import com.jungko.jungko_server.card.domain.CardScope;
import com.jungko.jungko_server.member.dto.MemberProfileDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
@Builder
@Schema(description = "카드 Preview를 담는 DTO")
public class CardPreviewDto {

  @Schema(description = "카드 ID", example = "1")
  private final Long cardId;

  @Schema(description = "카드 제목", example = "제목")
  private final String title;

  @Schema(description = "카드 검색 키워드", example = "검색 키워드")
  private final String keyword;

  @Schema(description = "최소 가격", example = "10000")
  private final Integer minPrice;

  @Schema(description = "최대 가격", example = "20000")
  private final Integer maxPrice;

  @Schema(description = "카드 공개 범위", example = "PUBLIC")
  private final CardScope scope;

  @Schema(description = "카드 생성 시간", example = "2021-08-01T00:00:00")
  private final LocalDateTime createdAt;

  @Schema(description = "카드 작성자 정보", implementation = MemberProfileDto.class)
  private final MemberProfileDto author;

  @Schema(description = "지역 정보", implementation = AreaDto.class)
  private final AreaDto area;
}

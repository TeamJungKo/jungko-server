package com.jungko.jungko_server.notification.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

import com.jungko.jungko_server.notification.domain.NoticeType;

@AllArgsConstructor
@Getter
@ToString
@Builder
@Schema(description = "카드 알림에 대한 DTO")
public class KeywordNoticeDto {

  @Schema(description = "notice 고유 번호", example = "12345")
  private final Long noticeId;

  @Schema(description = "notice 타입에 대한 정보(enum)", example = "KEYWORD")
  private final NoticeType noticeType;

  @Schema(description = "keyword 고유 번호", example = "1")
  private final Long keywordId;

  @Schema(description = "키워드", example = "아이유")
  private final String keyword;

  @Schema(description = "상품 고유 번호", example = "123")
  private final Long productId;

  @Schema(description = "상품 이름", example = "아이유 굿즈 팝니다")
  private final String productTitle;

  @Schema(description = "상품 이미지 경로", example = "https://example.com")
  private final String productImageUrl;

  @Schema(description = "알림 생성 시간", example = "2023-11-04T12:34:56Z")
  private final LocalDateTime createdAt;

  @Schema(description = "알림 읽음 여부", example = "false")
  private final Boolean isRead;
}

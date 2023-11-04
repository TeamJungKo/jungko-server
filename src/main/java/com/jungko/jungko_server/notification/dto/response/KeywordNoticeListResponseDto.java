package com.jungko.jungko_server.notification.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import java.util.List;

import com.jungko.jungko_server.notification.dto.KeywordNoticeDto;

@AllArgsConstructor
@Getter
@ToString
@Builder
@Schema(description = "전체 키워드 알림을 조회하는 DTO")
public class KeywordNoticeListResponseDto {
  @Schema(description = "키워드 notice 타입의 배열")
  private final List<KeywordNoticeDto> cardNotices;

  @Schema(description = "전체 키워드 알림수", example = "42")
  private final Integer totalResources;
}

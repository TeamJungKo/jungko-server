package com.jungko.jungko_server.notification.dto.response;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import java.util.List;

import com.jungko.jungko_server.notification.dto.CardNoticeDto;

@AllArgsConstructor
@Getter
@ToString
@Builder
@Schema(description = "전체 카드 알림을 조회하는 DTO")
public class CardNoticeListResponseDto {

  @ArraySchema(schema = @Schema(implementation = CardNoticeDto.class))
  private final List<CardNoticeDto> cardNotices;

  @Schema(description = "전체 카드 알림수", example = "42")
  private final Integer totalResources;
}

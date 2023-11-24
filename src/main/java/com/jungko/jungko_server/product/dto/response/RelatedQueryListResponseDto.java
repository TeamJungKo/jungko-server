package com.jungko.jungko_server.product.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import java.util.List;

@AllArgsConstructor
@Getter
@ToString
@Builder
@Schema(description = "현재 검색 결과의 연관 검색어를 제공하는 DTO")
public class RelatedQueryListResponseDto {

  @Schema(description = "연관검색어", type = "array", example = "[\"후드티\", \"후드\", \"후드집업\"]")
  private final List<String> relatedQueries;
}

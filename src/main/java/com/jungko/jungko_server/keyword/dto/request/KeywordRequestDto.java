package com.jungko.jungko_server.keyword.dto.request;

import com.jungko.jungko_server.keyword.validation.KeywordCreateValidation;
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

@Schema(description = "새 키워드들을 생성하는 DTO")
public class KeywordRequestDto {
  @Schema(description = "키워드Id", example = "[\"검정바지\", \"흰색바지\"]")
  private final List<String> keywords;
}

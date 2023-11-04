package com.jungko.jungko_server.keyword.dto.response;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import java.util.List;

import com.jungko.jungko_server.keyword.dto.KeywordDto;

@AllArgsConstructor
@Getter
@ToString
@Builder
@Schema(description = "키워드 목록을 조회하는 DTO")
public class KeywordListResponseDto {

  @ArraySchema(schema = @Schema(implementation = KeywordDto.class))
  private final List<KeywordDto> keywordList;
}

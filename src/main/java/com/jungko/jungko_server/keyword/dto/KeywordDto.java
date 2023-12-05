package com.jungko.jungko_server.keyword.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import java.util.List;

import com.jungko.jungko_server.keyword.domain.Keyword;
import com.jungko.jungko_server.member.dto.MemberProfileDto;
import io.swagger.v3.oas.annotations.media.Schema;

@AllArgsConstructor
@Getter
@ToString
@Builder
@Schema(description = "키워드 정보를 담는 DTO")
public class KeywordDto {

  @Schema(description = "키워드 ID", example = "1")
  private final Long keywordId;

  @Schema(description = "키워드", example = "검정바지")
  private final String keyword;

  @Schema(description = "키워드 작성자 정보", implementation = MemberProfileDto.class)
  private final MemberProfileDto author;
}

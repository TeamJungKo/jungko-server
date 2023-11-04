package com.jungko.jungko_server.keyword.dto.request;

import org.springframework.web.multipart.MultipartFile;

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

@Schema(description = "새 키워드들을 삭제하는 DTO")
public class KeywordDeleteRequestDto {

  @Schema(description = "키워드Id", example = "1")
  private final List<Long> keywordIds;
}

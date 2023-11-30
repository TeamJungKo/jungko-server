package com.jungko.jungko_server.keyword.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "새 키워드들을 생성하는 DTO")
public class KeywordRequestDto {

	@Schema(description = "새 키워드 배열", type = "array", example = "[\"검정바지\", \"흰색바지\"]")
	private List<String> keywords;
}

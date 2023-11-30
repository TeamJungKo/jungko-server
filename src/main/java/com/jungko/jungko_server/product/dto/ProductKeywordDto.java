package com.jungko.jungko_server.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
@Builder
@Schema(description = "상품의 키워드 정보를 조회하는 DTO")
public class ProductKeywordDto {

	@Schema(description = "키워드 ID", example = "1")
	private final Long id;

	@Schema(description = "키워드", example = "키워드")
	private final String keyword;
}

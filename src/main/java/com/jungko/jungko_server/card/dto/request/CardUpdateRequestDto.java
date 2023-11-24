package com.jungko.jungko_server.card.dto.request;

import com.jungko.jungko_server.card.domain.CardScope;

import com.jungko.jungko_server.card.validation.CardUpdateValidation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
@Builder
@Schema(description = "카드를 수정하는 DTO")
@CardUpdateValidation
public class CardUpdateRequestDto {

	@Schema(description = "카테고리 아이디", example = "1")
	private final Long categoryId;

	@Schema(description = "지역 아이디", example = "1")
	private final Long areaId;

	@Schema(description = "카드 제목", example = "검정바지")
	private final String title;

	@Schema(description = "카드 검색 키워드", example = "검정바지")
	private final String keyword;

	@Schema(description = "최소 가격", example = "10000")
	private final Integer minPrice;

	@Schema(description = "최대 가격", example = "20000")
	private final Integer maxPrice;

	@Schema(description = "카드 공개 범위", example = "PUBLIC")
	private final CardScope scope;
}

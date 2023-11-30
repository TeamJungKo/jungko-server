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
@Schema(description = "특정 상품 카테고리 목록을 조회하는 DTO")
public class SpecificProductCategoryDto {

	@Schema(description = "상품ID", example = "1")
	private final Long categoryId;

	@Schema(description = "상품ID", example = "뷰티")
	private final String name;

	@Schema(description = "상품ID", example = "1")
	private final Integer level;

	@Schema(description = "상품 카테고리 이미지 주소", example = "http://example.com")
	private final String imageUrl;

	@Schema(implementation = ProductCategoryDto.class)
	@ToString.Exclude
	private SpecificProductCategoryDto subCategory;

	public void setSubCategory(SpecificProductCategoryDto subCategory) {
		this.subCategory = subCategory;
	}
}

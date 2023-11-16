package com.jungko.jungko_server.product.dto.response;

import java.util.List;

import com.jungko.jungko_server.product.dto.ProductCategoryDto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
@Builder
@Schema(description = "상품 카테고리 리스트를 응답하는 DTO")
public class ProductCategoryListResponseDto {

	@ArraySchema(schema = @Schema(implementation = ProductCategoryDto.class))
	@ToString.Exclude
	private final List<ProductCategoryDto> productCategories;
}

package com.jungko.jungko_server.product.dto;

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
@Schema(description = "전체 상품 카테고리 목록을 조회하는 DTO")
public class ProductCategoryDto {
  @Schema(description = "상품ID", example = "1")
  private final Long categoryId;

  @Schema(description = "상품ID", example = "뷰티")
  private final String name;

  @Schema(description = "상품ID", example = "1")
  private final Integer level;

  @Schema(implementation = ProductCategoryDto.class)
  private final List<ProductCategoryDto> subCategory;
}

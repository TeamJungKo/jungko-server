package com.jungko.jungko_server.product.dto;

import com.jungko.jungko_server.area.dto.SpecificAreaDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import java.time.LocalDateTime;
import com.jungko.jungko_server.product.domain.ProductAvailability;

@AllArgsConstructor
@Getter
@ToString
@Builder
@Schema(description = "상품 미리보기를 응답하는 DTO")
public class ProductPreviewDto {

	@Schema(description = "상품ID", example = "1")
	private final Long productId;

	@Schema(description = "상품제목", example = "로션 팔아요")
	private final String title;

	@Schema(description = "상품내용", example = "게시글 내용")
	private final String content;

	@Schema(description = "상품 가격", example = "5000")
	private final Integer price;

	@Schema(description = "상품구매가능여부", example = "ON_SALE")
	private final ProductAvailability availability;

	@Schema(description = "상품게시날짜", example = "2023-10-20T11:24:36.069Z")
	private final LocalDateTime uploadedAt;

	@Schema(description = "상품이미지", example = "https::example.com")
	private final String productImageUrl;

	@Schema(description = "마켓이름", example = "당근마켓")
	private final String marketName;

	@Schema(description = "마켓상품ID", example = "1234")
	private final String marketProductId;

	@Schema(description = "카테고리 정보", implementation = SpecificProductCategoryDto.class)
	private final SpecificProductCategoryDto category;

	@Schema(description = "지역 정보", implementation = SpecificAreaDto.class)
	private final SpecificAreaDto area;

	@Schema(description = "상품의 키워드 정보를 조회하는 DTO", implementation = ProductKeywordDto.class)
	private final List<ProductKeywordDto> keywords;
}

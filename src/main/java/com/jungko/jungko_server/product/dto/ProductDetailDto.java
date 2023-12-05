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
@Schema(description = "상품의 상세 정보를 조회하는 DTO")
public class ProductDetailDto {

	@Schema(description = "상품ID", example = "1")
	private final Long productId;

	@Schema(description = "상품제목", example = "로션 팔아요")
	private final String title;

	@Schema(description = "상품내용", example = "대충 게시글 내용")
	private final String content;

	@Schema(description = "상품가격", example = "5000")
	private final Integer price;

	@Schema(description = "상품구매가능성", example = "ON_SALE")
	private final ProductAvailability availability;

	@Schema(description = "상품업로드날짜시간", example = "2023-10-20T11:24:36.069Z")
	private final LocalDateTime uploadedAt;

	@Schema(description = "상품의 이미지 URL", example = "https://www.google.com")
	private final String productImageUrl;

	@Schema(description = "상품의 마켓 이름", example = "당근")
	private final String marketName;

	@Schema(description = "해당 마켓에서의 상품 ID", example = "1")
	private final String marketProductId;

	@Schema(description = "상품의 마켓 URL", example = "https://www.google.com")
	private final String marketProductUrl;

	@Schema(description = "상품의 상세 카테고리 정보를 조회하는 DTO", implementation = SpecificProductCategoryDto.class)
	private final SpecificProductCategoryDto category;

	@Schema(description = "상품의 상세 지역 정보를 조회하는 DTO", implementation = SpecificAreaDto.class)
	private final SpecificAreaDto area;

	@Schema(description = "상품의 키워드 정보를 조회하는 DTO", implementation = ProductKeywordDto.class)
	private final List<ProductKeywordDto> keywords;

	@Schema(description = "생성일", example = "2023-10-20T11:24:36.069Z")
	private final LocalDateTime createdAt;
}

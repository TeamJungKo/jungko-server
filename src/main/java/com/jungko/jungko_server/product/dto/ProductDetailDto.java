package com.jungko.jungko_server.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import java.time.LocalDateTime;
import com.jungko.jungko_server.area.dto.AreaDto;
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

  @Schema(description = "상품의 상세 정보를 조회하는 DTO", example = "https::example.com")
  private final String productImageUrl;

  @Schema(description = "상품의 상세 정보를 조회하는 DTO", example = "당근마켓")
  private final String marketName;

  @Schema(description = "상품의 상세 정보를 조회하는 DTO", example = "1234")
  private final String marketProductId;

  @Schema(description = "상품의 상세 정보를 조회하는 DTO", example = "1")
  private final ProductCategoryDto productCategory;

  @Schema(description = "상품의 상세 정보를 조회하는 DTO", example = "1")
  private final AreaDto area;

  @Schema(description = "상품의 상세 정보를 조회하는 DTO", example = "1")
  private final LocalDateTime createdAt;
}

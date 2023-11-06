package com.jungko.jungko_server.product.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import com.jungko.jungko_server.product.dto.ProductDetailDto;

@AllArgsConstructor
@Getter
@ToString
@Builder
@Schema(description = "상품 상세 조회 응답")
public class ProductDetailResponseDto {

  @Schema(description = "상품 상세 정보", implementation = ProductDetailDto.class)
  private final ProductDetailDto productDetail;
}

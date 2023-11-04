package com.jungko.jungko_server.product.dto.response;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import java.util.List;
import com.jungko.jungko_server.product.dto.ProductDetailDto;

@AllArgsConstructor
@Getter
@ToString
@Builder
@Schema(description = "현재 검색 결과의 연관 검색어를 제공하는 DTO")
public class ProductDetailResponseDto {

  @ArraySchema(schema = @Schema(implementation = ProductDetailDto.class))
  private final List<ProductDetailDto> products;

  @Schema(description = "전체", example = "142")
  private final int totalResources;
}

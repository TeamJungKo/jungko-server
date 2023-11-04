package com.jungko.jungko_server.product.dto.response;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import java.util.List;

import com.jungko.jungko_server.product.dto.ProductPreviewDto;

@AllArgsConstructor
@Getter
@ToString
@Builder
@Schema(description = "상품 리스트를 응답하는 DTO")
public class ProductListResponseDto {

  @ArraySchema(schema = @Schema(implementation = ProductPreviewDto.class))
  private final List<ProductPreviewDto> products;

  @Schema(description = "전체", example = "142")
  private final Integer totalResources;
}

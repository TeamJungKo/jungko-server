package com.jungko.jungko_server.product.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Builder
@Schema(description = "상품들 비교하는 DTO")
public class ProductCompareRequestDto {

	@Schema(description = "상품Id", example = "1")
	private List<Long> productIds;
}


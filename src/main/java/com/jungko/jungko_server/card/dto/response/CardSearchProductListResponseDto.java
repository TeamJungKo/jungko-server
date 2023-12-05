package com.jungko.jungko_server.card.dto.response;

import com.jungko.jungko_server.member.dto.MemberProfileDto;
import com.jungko.jungko_server.product.dto.ProductPreviewDto;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
@Builder
@Schema(description = "카드 검색 상품 리스트를 응답하는 DTO")
public class CardSearchProductListResponseDto {

	@ArraySchema(schema = @Schema(implementation = ProductPreviewDto.class))
	private final List<ProductPreviewDto> products;

	@Schema(implementation = MemberProfileDto.class)
	private final MemberProfileDto author;

	@Schema(description = "전체", example = "142")
	private final int totalResources;
}

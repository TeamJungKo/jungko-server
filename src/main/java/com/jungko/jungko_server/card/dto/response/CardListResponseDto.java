package com.jungko.jungko_server.card.dto.response;

import com.jungko.jungko_server.card.dto.CardPreviewDto;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
@Schema(description = "전체 카드 목록을 조회하는 DTO")
public class CardListResponseDto {

	@ArraySchema(schema = @Schema(implementation = CardPreviewDto.class))
	private final List<CardPreviewDto> cards;

	@Schema(description = "전체 카드 수", example = "42")
	private final long totalResources;
}

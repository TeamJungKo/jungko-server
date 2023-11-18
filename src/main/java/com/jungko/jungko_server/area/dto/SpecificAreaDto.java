package com.jungko.jungko_server.area.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
@Builder
@Schema(description = "특정 지역 정보를 담는 DTO")
public class SpecificAreaDto {

	@Schema(description = "시도 이름", example = "서울특별시")
	private final String sido;

	@Schema(description = "시군구 이름", example = "강남구")
	private final String sigg;

	@Schema(description = "읍면동 이름", example = "삼성동")
	private final String emd;
}

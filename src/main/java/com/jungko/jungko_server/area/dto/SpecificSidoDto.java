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
public class SpecificSidoDto {

	@Schema(description = "시도 이름", example = "서울특별시")
	private final String name;

	@Schema(description = "시도 코드", example = "1")
	private final String code;

	@Schema(description = "시군구", implementation = SpecificSiggDto.class)
	private final SpecificSiggDto sigg;
}

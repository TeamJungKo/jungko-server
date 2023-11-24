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

	@Schema(description = "시도", implementation = SpecificSidoDto.class)
	private final SpecificSidoDto sido;
}


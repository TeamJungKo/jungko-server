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
public class SpecificEmdDto {

	@Schema(description = "읍면동 이름", example = "장충동2가")
	private final String name;

	@Schema(description = "읍면동 코드", example = "11140144")
	private final String code;
}

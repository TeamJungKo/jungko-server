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
public class SpecificSiggDto {

	@Schema(description = "시군구 이름", example = "중구")
	private final String name;

	@Schema(description = "시군구 코드", example = "11140")
	private final String code;

	@Schema(description = "읍면동", implementation = SpecificEmdDto.class)
	private final SpecificEmdDto emd;
}

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
@Schema(description = "읍면동 정보를 담는 DTO")
public class EmdDto {

	@Schema(description = "고유 ID", example = "1")
	private final Long id;

	@Schema(description = "읍면동 코드", example = "1111010100")
	private final String code;

	@Schema(description = "읍면동 이름", example = "행신동")
	private final String name;
}

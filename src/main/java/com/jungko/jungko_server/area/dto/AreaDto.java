package com.jungko.jungko_server.area.dto;

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
@Schema(description = "지역 정보를 담는 DTO")
public class AreaDto {

	//	@ArraySchema(schema = @Schema(implementation = SidoDto.class))
	@Schema(description = "시군구 목록", type = "Array")
	private final List<SidoDto> sido;
}

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
@Schema(description = "시도 정보를 담는 DTO")
public class SidoDto {

	@Schema(description = "시도 코드", example = "1111010100")
	private final String code;

	@Schema(description = "시도 이름", example = "경기도")
	private final String name;

	@ArraySchema(schema = @Schema(implementation = SiggDto.class))
	private final List<SiggDto> sigg;
}

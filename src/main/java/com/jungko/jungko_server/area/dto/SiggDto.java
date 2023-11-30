package com.jungko.jungko_server.area.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
@Builder
@Schema(description = "시군구 정보를 담는 DTO")
public class SiggDto {

	@Schema(description = "고유 ID", example = "1")
	private final Long id;

	@Schema(description = "시군구 코드", example = "1111010100")
	private final String code;

	@Schema(description = "시군구 이름", example = "고양시")
	private final String name;

	@ArraySchema(schema = @Schema(implementation = EmdDto.class))
	private final Set<EmdDto> emd;
}

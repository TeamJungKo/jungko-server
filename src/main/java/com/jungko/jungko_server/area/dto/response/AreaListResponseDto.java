package com.jungko.jungko_server.area.dto.response;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import java.util.List;

import com.jungko.jungko_server.area.dto.AreaDto;

@AllArgsConstructor
@Getter
@ToString
@Builder
@Schema(description = "지역 정보를 담는 DTO")
public class AreaListResponseDto {

	//	@ArraySchema(schema = @Schema(implementation = AreaDto.class))
	@Schema(description = "지역 정보", type = "array", implementation = AreaDto.class)
	private final List<AreaDto> areas;
}

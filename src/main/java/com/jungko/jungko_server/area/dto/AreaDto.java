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
@Schema(description = "지역 정보를 담는 DTO")
public class AreaDto {

  @Schema(description = "시도 정보", implementation = SidoDto.class)
  private final SidoDto sido;
}

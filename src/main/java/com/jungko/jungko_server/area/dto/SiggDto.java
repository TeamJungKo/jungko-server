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
@Schema(description = "시군구 정보를 담는 DTO")
public class SiggDto {

  @Schema(description = "시군구 코드", example = "1111010100")
  private final String code;

  @Schema(description = "시군구 이름", example = "고양시")
  private final String name;

  @Schema(description = "읍면동 정보", implementation = EmdDto.class)
  private final EmdDto emd;
}

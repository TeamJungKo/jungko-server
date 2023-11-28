package com.jungko.jungko_server.notification.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Builder
@Schema(description = "디바이스 토큰을 등록하는 DTO")
public class DeviceTokenRequestDto {

	@Schema(description = "디바이스 토큰", example = "1234")
	private String deviceToken;
}

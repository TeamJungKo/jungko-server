package com.jungko.jungko_server.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
@Builder
@Schema(description = "회원 프로필 정보를 응답하는 DTO")
public class MemberProfileDto {

  @Schema(description = "회원 ID", example = "1")
  private final Long memberId;

  @Schema(description = "회원 닉네임", example = "sichoi")
  private final String nickname;

  @Schema(description = "회원 프로필 이미지 URL", example = "https://example.com")
  private final String imageUrl;

  @Schema(description = "회원 이메일", example = "example@gmail.com")
  private final String email;
}

package com.jungko.jungko_server.member.dto.request;

import com.jungko.jungko_server.member.validation.MemberUpdateValidation;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
@Builder
@Schema(description = "회원 프로필 정보를 수정하는 DTO")
@MemberUpdateValidation
public class MemberProfileUpdateRequestDto {

	@Schema(description = "회원 닉네임", example = "sichoi")
	private final String nickname;

	@Schema(description = "프로필 이미지 데이터", example = "base64 encoded image data", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
	private final MultipartFile imageData;

	@Schema(description = "회원 이메일", example = "example.gmail.com")
	private final String email;
}

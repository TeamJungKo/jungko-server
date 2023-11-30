package com.jungko.jungko_server.member.validation;

import com.jungko.jungko_server.member.dto.request.MemberProfileUpdateRequestDto;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MemberUpdateValidator implements
		ConstraintValidator<MemberUpdateValidation, MemberProfileUpdateRequestDto> {

	private static final String NICKNAME_REGEX = "^[a-zA-Z0-9ㄱ-ㅎㅏ-ㅣ가-힣]+$";
	private static final Pattern NICKNAME_PATTERN = Pattern.compile(NICKNAME_REGEX);
	private static final int NICKNAME_MIN_LENGTH = 2;
	private static final int NICKNAME_MAX_LENGTH = 16;
	private static final String EMAIL_REGEX = "^[a-zA-Z0-9+-.]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+$";
	private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
	private static final int EMAIL_MAX_LENGTH = 64;
	private static final List<String> ALLOWED_IMAGE_TYPES = Arrays.asList(
			"jpeg", "jpg", "png");

	private boolean validateNickname(MemberProfileUpdateRequestDto value,
			ConstraintValidatorContext context) {
		if (value.getNickname() == null || value.getNickname().isEmpty()) {
			return true;
		}

		if (value.getNickname().length() < NICKNAME_MIN_LENGTH
				|| value.getNickname().length() > NICKNAME_MAX_LENGTH) {
			context.buildConstraintViolationWithTemplate("닉네임은 " + NICKNAME_MIN_LENGTH
							+ "자 이상 " + NICKNAME_MAX_LENGTH + "자 이하로 입력해주세요.")
					.addConstraintViolation();
			return false;
		}

		if (!NICKNAME_PATTERN.matcher(value.getNickname()).matches()) {
			context.buildConstraintViolationWithTemplate("닉네임은 한글, 영문, 숫자만 입력 가능합니다.")
					.addConstraintViolation();
			return false;
		}
		return true;
	}

	private boolean validateEmail(MemberProfileUpdateRequestDto value,
			ConstraintValidatorContext context) {
		if (value.getEmail() == null || value.getEmail().isEmpty()) {
			return true;
		}

		if (value.getEmail().length() > EMAIL_MAX_LENGTH) {
			context.buildConstraintViolationWithTemplate("이메일은 " + EMAIL_MAX_LENGTH
							+ "자 이하로 입력해주세요.")
					.addConstraintViolation();
			return false;
		}

		if (!EMAIL_PATTERN.matcher(value.getEmail()).matches()) {
			context.buildConstraintViolationWithTemplate("이메일 형식이 올바르지 않습니다.")
					.addConstraintViolation();
			return false;
		}
		return true;
	}

	private boolean validateImage(MemberProfileUpdateRequestDto value,
			ConstraintValidatorContext context) {
		if (value.getImageData() == null || value.getImageData().isEmpty() || value
				.getImageData().getSize() == 0
				|| value.getImageData().getOriginalFilename() == null) {
			return true;
		}

		String originalFilename = value.getImageData().getOriginalFilename();
		String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
		if (!ALLOWED_IMAGE_TYPES.contains(fileExtension.toLowerCase())) {
			context.buildConstraintViolationWithTemplate(
							"이미지 파일은 jpg, jpeg, png 형식만 가능합니다.")
					.addConstraintViolation();
			return false;
		}
		return true;
	}

	@Override
	public boolean isValid(MemberProfileUpdateRequestDto value,
			ConstraintValidatorContext context) {
		return validateNickname(value, context)
				&& validateEmail(value, context)
				&& validateImage(value, context);
	}
}

package com.jungko.jungko_server.keyword.validation;

import com.jungko.jungko_server.keyword.dto.request.KeywordRequestDto;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
public class KeywordCreateValidator implements
		ConstraintValidator<KeywordCreateValidation, KeywordRequestDto> {
	private static final int KEYWORD_MAX_LENGTH = 16;

	private boolean validateKeyword(String keyword, ConstraintValidatorContext context) {
		if (keyword.length() > KEYWORD_MAX_LENGTH) {
			context.buildConstraintViolationWithTemplate("키워드는" + KEYWORD_MAX_LENGTH + "자 이하로 입력해주세요.").addConstraintViolation();
			return false;
		}
		return true;
	}
	private boolean validateKeywords(KeywordRequestDto value, ConstraintValidatorContext context) {
		return value.getKeywords().stream()
				.map(keyword -> validateKeyword(keyword, context)).isParallel();
	}

	@Override
	public boolean isValid(KeywordRequestDto value,ConstraintValidatorContext context) {
		return validateKeywords(value, context);
	}
}
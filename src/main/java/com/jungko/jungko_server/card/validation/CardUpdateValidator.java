package com.jungko.jungko_server.card.validation;

import com.jungko.jungko_server.card.dto.request.CardUpdateRequestDto;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CardUpdateValidator implements
		ConstraintValidator<CardUpdateValidation, CardUpdateRequestDto> {

	private static final int TITLE_MAX_LENGTH = 32;
	private static final int KEYWORD_MAX_LENGTH = 16;
	private static final int MIN_PRICE_VALUE = 0;
	private static final int MAX_PRICE_VALUE = 100000000;

	private boolean validateTitle(CardUpdateRequestDto value,
			ConstraintValidatorContext context) {
		if (value.getTitle() != null && !value.getTitle().isEmpty()) {
			if (value.getTitle().length() > TITLE_MAX_LENGTH) {
				context.buildConstraintViolationWithTemplate(
								"카드 제목은 " + TITLE_MAX_LENGTH + "자 이하로 입력해주세요.")
						.addConstraintViolation();
				return false;
			}
		}
		return true;
	}

	private boolean validateKeyword(CardUpdateRequestDto value,
			ConstraintValidatorContext context) {
		if (value.getKeyword() != null && !value.getKeyword().isEmpty()) {
			if (value.getKeyword().length() > KEYWORD_MAX_LENGTH) {
				context.buildConstraintViolationWithTemplate(
								"카드 검색 키워드는 " + KEYWORD_MAX_LENGTH + "자 이하로 입력해주세요.")
						.addConstraintViolation();
				return false;
			}
		}
		return true;
	}

	private boolean validatePrice(CardUpdateRequestDto value,
			ConstraintValidatorContext context) {
		if (value.getMinPrice() != null && value.getMinPrice() < MIN_PRICE_VALUE) {
			context.buildConstraintViolationWithTemplate(
							"최소 가격은 " + MIN_PRICE_VALUE + "원 이상으로 입력해주세요.")
					.addConstraintViolation();
			return false;
		}

		if (value.getMaxPrice() != null && value.getMaxPrice() > MAX_PRICE_VALUE) {
			context.buildConstraintViolationWithTemplate("최대 가격은 " + MAX_PRICE_VALUE
							+ "원 이하로 입력해주세요.")
					.addConstraintViolation();
			return false;
		}
		return true;
	}

	@Override
	public boolean isValid(CardUpdateRequestDto value,
			ConstraintValidatorContext context) {
		return validateTitle(value, context)
				&& validateKeyword(value, context)
				&& validatePrice(value, context);
	}
}

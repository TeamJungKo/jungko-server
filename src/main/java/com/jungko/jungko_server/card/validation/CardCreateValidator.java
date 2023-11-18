package com.jungko.jungko_server.card.validation;

import com.jungko.jungko_server.card.dto.request.CardCreateRequestDto;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CardCreateValidator implements
		ConstraintValidator<CardCreateValidation, CardCreateRequestDto> {


	private static final int TITLE_MAX_LENGTH = 32;
	private static final int KEYWORD_MAX_LENGTH = 16;
	private static final int MIN_PRICE_VALUE = 0;
	private static final int MAX_PRICE_VALUE = 100000000;

	private boolean validateTitle(CardCreateRequestDto value,
			ConstraintValidatorContext context) {
		if (value.getTitle() == null || value.getTitle().isEmpty()) {
			context.buildConstraintViolationWithTemplate("카드 제목을 입력해주세요.")
					.addConstraintViolation();
			return false;
		}

		if (value.getTitle().length() > TITLE_MAX_LENGTH) {
			context.buildConstraintViolationWithTemplate(
							"카드 제목은 " + TITLE_MAX_LENGTH + "자 이하로 입력해주세요.")
					.addConstraintViolation();
			return false;
		}
		return true;
	}

	private boolean validateKeyword(CardCreateRequestDto value,
			ConstraintValidatorContext context) {
		if (value.getKeyword() == null || value.getKeyword().isEmpty()) {
			context.buildConstraintViolationWithTemplate("카드 검색 키워드를 입력해주세요.")
					.addConstraintViolation();
			return false;
		}

		if (value.getKeyword().length() > KEYWORD_MAX_LENGTH) {
			context.buildConstraintViolationWithTemplate(
							"카드 검색 키워드는 " + KEYWORD_MAX_LENGTH + "자 이하로 입력해주세요.")
					.addConstraintViolation();
			return false;
		}
		return true;
	}

	private boolean validatePrice(CardCreateRequestDto value,
			ConstraintValidatorContext context) {
		if (value.getMinPrice() == null || value.getMinPrice() < MIN_PRICE_VALUE) {
			context.buildConstraintViolationWithTemplate(
							"최소 가격은 " + MIN_PRICE_VALUE + "원 이상으로 입력해주세요.")
					.addConstraintViolation();
			return false;
		}

		if (value.getMaxPrice() == null || value.getMaxPrice() > MAX_PRICE_VALUE) {
			context.buildConstraintViolationWithTemplate("최대 가격은 " + MAX_PRICE_VALUE
							+ "원 이하로 입력해주세요.")
					.addConstraintViolation();
			return false;
		}

		if (value.getMinPrice() > value.getMaxPrice()) {
			context.buildConstraintViolationWithTemplate("최소 가격은 최대 가격보다 작아야 합니다.")
					.addConstraintViolation();
			return false;
		}
		return true;
	}

	@Override
	public boolean isValid(CardCreateRequestDto value,
			ConstraintValidatorContext context) {
		return validateTitle(value, context)
				&& validateKeyword(value, context)
				&& validatePrice(value, context);
	}
}

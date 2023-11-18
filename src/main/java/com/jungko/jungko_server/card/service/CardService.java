package com.jungko.jungko_server.card.service;

import com.jungko.jungko_server.area.domain.EmdArea;
import com.jungko.jungko_server.area.infrastructure.EmdAreaRepository;
import com.jungko.jungko_server.card.domain.Card;
import com.jungko.jungko_server.card.dto.CardPreviewDto;
import com.jungko.jungko_server.card.dto.request.CardCreateRequestDto;
import com.jungko.jungko_server.card.infrastructure.CardRepository;
import com.jungko.jungko_server.member.domain.Member;
import com.jungko.jungko_server.member.infrastructure.MemberRepository;
import com.jungko.jungko_server.product.domain.ProductCategory;
import com.jungko.jungko_server.product.infrastructure.ProductCategoryRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;


@Service
@RequiredArgsConstructor
@Slf4j
public class CardService {

	private final CardRepository cardRepository;
	private final MemberRepository memberRepository;
	private final ProductCategoryRepository productCategoryRepository;
	private final EmdAreaRepository emdAreaRepository;

	public CardPreviewDto createCard(Long memberId, CardCreateRequestDto dto) {
		log.info("Called createCard memberId: {}, dto: {}", memberId, dto);

		Member loginMember = memberRepository.findById(memberId).orElseThrow(
				() -> new HttpClientErrorException(
						HttpStatus.NOT_FOUND,
						"해당 회원이 존재하지 않습니다. id=" + memberId));

		ProductCategory productCategory = productCategoryRepository.findById(dto.getCategoryId())
				.orElseThrow(
						() -> new HttpClientErrorException(
								HttpStatus.NOT_FOUND,
								"해당 상품 카테고리가 존재하지 않습니다. id=" + dto.getCategoryId()));

		EmdArea emdArea = emdAreaRepository.findById(dto.getAreaId()).orElseThrow(
				() -> new HttpClientErrorException(
						HttpStatus.NOT_FOUND,
						"해당 지역이 존재하지 않습니다. id=" + dto.getAreaId()));

		Card createdCard = Card.createCard(
				dto.getTitle(),
				dto.getKeyword(),
				dto.getMinPrice(),
				dto.getMaxPrice(),
				dto.getScope(),
				LocalDateTime.now()
		);

		return CardPreviewDto.builder().build();
	}
}

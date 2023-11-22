package com.jungko.jungko_server.card.service;

import com.jungko.jungko_server.area.domain.EmdArea;
import com.jungko.jungko_server.area.dto.SpecificAreaDto;
import com.jungko.jungko_server.area.infrastructure.EmdAreaRepository;
import com.jungko.jungko_server.card.domain.Card;
import com.jungko.jungko_server.card.dto.CardPreviewDto;
import com.jungko.jungko_server.card.dto.request.CardCreateRequestDto;
import com.jungko.jungko_server.card.dto.request.CardUpdateRequestDto;
import com.jungko.jungko_server.card.dto.response.CardListResponseDto;
import com.jungko.jungko_server.card.infrastructure.CardRepository;
import com.jungko.jungko_server.mapper.AreaMapper;
import com.jungko.jungko_server.mapper.CardMapper;
import com.jungko.jungko_server.mapper.MemberMapper;
import com.jungko.jungko_server.mapper.ProductMapper;
import com.jungko.jungko_server.member.domain.Member;
import com.jungko.jungko_server.member.dto.MemberProfileDto;
import com.jungko.jungko_server.member.infrastructure.MemberRepository;
import com.jungko.jungko_server.product.domain.ProductCategory;
import com.jungko.jungko_server.product.dto.SpecificProductCategoryDto;
import com.jungko.jungko_server.product.infrastructure.ProductCategoryRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CardService {

	private final CardRepository cardRepository;
	private final MemberRepository memberRepository;
	private final ProductCategoryRepository productCategoryRepository;
	private final EmdAreaRepository emdAreaRepository;
	private final MemberMapper memberMapper;
	private final AreaMapper areaMapper;
	private final ProductMapper productMapper;
	private final CardMapper cardMapper;

	public CardPreviewDto createCard(Long memberId, CardCreateRequestDto dto) {
		log.info("Called createCard memberId: {}, dto: {}", memberId, dto);

		Member loginMember = memberRepository.findById(memberId).orElseThrow(
				() -> new HttpClientErrorException(
						HttpStatus.NOT_FOUND,
						"해당 회원이 존재하지 않습니다. id=" + memberId));
		ProductCategory productCategory = productCategoryRepository.findByIdAndFetchParentEagerly(
						dto.getCategoryId())
				.orElseGet(() -> productCategoryRepository.findById(dto.getCategoryId())
						.orElseThrow(
								() -> new HttpClientErrorException(
										HttpStatus.NOT_FOUND,
										"해당 상품 카테고리가 존재하지 않습니다. id=" + dto.getCategoryId())));
		EmdArea emdArea = emdAreaRepository.findEmdAreaByIdWithSiggAreaWithSidoArea(dto.getAreaId())
				.orElseThrow(
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
		createdCard.setOwner(loginMember);
		createdCard.setProductCategory(productCategory);
		createdCard.setArea(emdArea);
		Card savedCard = cardRepository.save(createdCard);

		MemberProfileDto author = memberMapper.toMemberProfileDto(savedCard.getMember(),
				savedCard.getMember().getProfileImageUrl());
		SpecificAreaDto areaDto = areaMapper.emdAreaToSpecificAreaDto(savedCard.getArea());
		SpecificProductCategoryDto categoryDto = productMapper
				.convertToSpecificProductCategoryDtoRecursive(
						savedCard.getProductCategory());
		return cardMapper.toCardPreviewDto(savedCard, author, areaDto, categoryDto);
	}

	public void deleteCard(Long memberId, Long cardId) {
		log.info("Called deleteCard memberId: {}, cardId: {}", memberId, cardId);

		Member loginMember = memberRepository.findById(memberId).orElseThrow(
				() -> new HttpClientErrorException(
						HttpStatus.NOT_FOUND,
						"해당 회원이 존재하지 않습니다. id=" + memberId));

		Card card = cardRepository.findById(cardId).orElseThrow(
				() -> new HttpClientErrorException(
						HttpStatus.NOT_FOUND,
						"해당 카드가 존재하지 않습니다. id=" + cardId));

		System.out.println(card);

		if (!card.isOwner(loginMember)) {
			throw new HttpClientErrorException(
					HttpStatus.FORBIDDEN,
					"해당 카드의 소유자가 아닙니다. cardId=" + cardId + ", memberId=" + memberId);
		}

		cardRepository.delete(card);
	}

	public void updateCard(Long memberId, CardUpdateRequestDto dto, Long cardId) {
		log.info("Called updateCard memberId: {}, dto: {}, cardId: {}", memberId, dto, cardId);

		Member loginMember = memberRepository.findById(memberId).orElseThrow(
				() -> new HttpClientErrorException(
						HttpStatus.NOT_FOUND,
						"해당 회원이 존재하지 않습니다. id=" + memberId));

		Card card = cardRepository.findById(cardId).orElseThrow(
				() -> new HttpClientErrorException(
						HttpStatus.NOT_FOUND,
						"해당 카드가 존재하지 않습니다. id=" + cardId));

		if (!card.isOwner(loginMember)) {
			throw new HttpClientErrorException(
					HttpStatus.FORBIDDEN,
					"해당 카드의 소유자가 아닙니다. cardId=" + cardId + ", memberId=" + memberId);
		}

		card.update(
				dto.getTitle(),
				dto.getKeyword(),
				dto.getMinPrice(),
				dto.getMaxPrice(),
				dto.getScope()
		);
		cardRepository.save(card);
	}

	public CardListResponseDto getCardsByMemberId(Long memberId, PageRequest pageRequest) {
		log.info("Called getMyCards memberId: {}, pageRequest: {}", memberId, pageRequest);

		Member loginMember = memberRepository.findById(memberId).orElseThrow(
				() -> new HttpClientErrorException(
						HttpStatus.NOT_FOUND,
						"해당 회원이 존재하지 않습니다. id=" + memberId));
		Page<Card> cards = cardRepository.findAllByMemberId(loginMember.getId(), pageRequest);

		List<CardPreviewDto> cardPreviewDtos = cards.stream().map(card -> {
					MemberProfileDto author = memberMapper.toMemberProfileDto(card.getMember(),
							card.getMember().getProfileImageUrl());
					SpecificAreaDto areaDto = areaMapper.emdAreaToSpecificAreaDto(card.getArea());
					SpecificProductCategoryDto categoryDto = productMapper
							.convertToSpecificProductCategoryDtoRecursive(
									card.getProductCategory());
					return cardMapper.toCardPreviewDto(card, author, areaDto, categoryDto);
				}
		).collect(Collectors.toList());
		return cardMapper.toCardListResponseDto(cardPreviewDtos, cards.getTotalElements());
	}

	public CardListResponseDto getPopularCards(PageRequest pageRequest) {
		log.info("Called getPopularCards pageRequest: {}", pageRequest);

		Page<Card> cards = cardRepository.findAllByInterestedCardsCount(pageRequest);

		List<CardPreviewDto> cardPreviewDtos = cards.stream().map(card -> {
					MemberProfileDto author = memberMapper.toMemberProfileDto(card.getMember(),
							card.getMember().getProfileImageUrl());
					SpecificAreaDto areaDto = areaMapper.emdAreaToSpecificAreaDto(card.getArea());
					SpecificProductCategoryDto categoryDto = productMapper
							.convertToSpecificProductCategoryDtoRecursive(
									card.getProductCategory());
					return cardMapper.toCardPreviewDto(card, author, areaDto, categoryDto);
				}
		).collect(Collectors.toList());
		return cardMapper.toCardListResponseDto(cardPreviewDtos, cards.getTotalElements());
	}
}

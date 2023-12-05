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
import com.jungko.jungko_server.product.domain.Product;
import com.jungko.jungko_server.product.domain.ProductCategory;
import com.jungko.jungko_server.product.dto.ProductKeywordDto;
import com.jungko.jungko_server.product.dto.ProductPreviewDto;
import com.jungko.jungko_server.product.dto.SpecificProductCategoryDto;
import com.jungko.jungko_server.product.dto.response.ProductListResponseDto;
import com.jungko.jungko_server.product.infrastructure.ProductCategoryRepository;
import com.jungko.jungko_server.product.infrastructure.ProductRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
	private final ProductRepository productRepository;
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

	public CardListResponseDto getCardsByMemberId(Long memberId, PageRequest pageRequest,
			Long categoryId) {
		log.info("Called getMyCards memberId: {}, pageRequest: {}, categoryId: {}", memberId,
				pageRequest, categoryId);

		Member loginMember = memberRepository.findById(memberId).orElseThrow(
				() -> new HttpClientErrorException(
						HttpStatus.NOT_FOUND,
						"해당 회원이 존재하지 않습니다. id=" + memberId));
		Page<Card> cards = cardRepository.findAllByMemberId(loginMember.getId(), pageRequest);
		Page<Card> filteredCards = cards.stream()
				.filter(card -> categoryId == null || card.getProductCategory().getId()
						.equals(categoryId))
				.collect(Collectors.collectingAndThen(Collectors.toList(),
						list -> new PageImpl<>(list, pageRequest, cards.getTotalElements())));

		List<CardPreviewDto> cardPreviewDtos = filteredCards.stream()
				.filter(card -> categoryId == null || card.getProductCategory().getId()
						.equals(categoryId))
				.map(card -> {
							MemberProfileDto author = memberMapper.toMemberProfileDto(card.getMember(),
									card.getMember().getProfileImageUrl());
							SpecificAreaDto areaDto = areaMapper.emdAreaToSpecificAreaDto(card.getArea());
							SpecificProductCategoryDto categoryDto = productMapper
									.convertToSpecificProductCategoryDtoRecursive(
											card.getProductCategory());
							return cardMapper.toCardPreviewDto(card, author, areaDto, categoryDto);
						}
				).collect(Collectors.toList());
		return cardMapper.toCardListResponseDto(cardPreviewDtos, filteredCards.getTotalElements());
	}

	public CardListResponseDto getPopularCards(PageRequest pageRequest, Long categoryId) {
		log.info("Called getPopularCards pageRequest: {}, categoryId: {}", pageRequest,
				categoryId);

		Page<Card> cards = cardRepository.findAllByInterestedCardsCount(pageRequest);
		Page<Card> filteredCards = cards.stream()
				.filter(card -> categoryId == null || card.getProductCategory().getId()
						.equals(categoryId))
				.collect(Collectors.collectingAndThen(Collectors.toList(),
						list -> new PageImpl<>(list, pageRequest, cards.getTotalElements())));

		List<CardPreviewDto> cardPreviewDtos = filteredCards.stream()
				.filter(card -> categoryId == null || card.getProductCategory().getId()
						.equals(categoryId))
				.map(card -> {
							MemberProfileDto author = memberMapper.toMemberProfileDto(card.getMember(),
									card.getMember().getProfileImageUrl());
							SpecificAreaDto areaDto = areaMapper.emdAreaToSpecificAreaDto(card.getArea());
							SpecificProductCategoryDto categoryDto = productMapper
									.convertToSpecificProductCategoryDtoRecursive(
											card.getProductCategory());
							return cardMapper.toCardPreviewDto(card, author, areaDto, categoryDto);
						}
				).collect(Collectors.toList());
		return cardMapper.toCardListResponseDto(cardPreviewDtos, filteredCards.getTotalElements());
	}

	public ProductListResponseDto searchProductsByCard(Long memberId, Long cardId,
			Pageable pageable) {
		log.info("Called searchProductsByCard memberId: {}, cardId: {}, pageable: {}",
				memberId, cardId, pageable);

		Member loginMember = memberRepository.findById(memberId).orElseThrow(
				() -> new HttpClientErrorException(
						HttpStatus.NOT_FOUND,
						"해당 회원이 존재하지 않습니다. id=" + memberId));
		Card card = cardRepository.findById(cardId).orElseThrow(
				() -> new HttpClientErrorException(
						HttpStatus.NOT_FOUND,
						"해당 카드가 존재하지 않습니다. id=" + cardId));

		System.out.println(card.getProductCategory());

		Specification<Product> specification = (root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();

			if (card.getKeyword() != null) {
				predicates.add(
						criteriaBuilder.like(root.get("title"), "%" + card.getKeyword() + "%"));
			}
			if (card.getMinPrice() != null) {
				predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"),
						card.getMinPrice()));
			}
			if (card.getMaxPrice() != null) {
				predicates.add(
						criteriaBuilder.lessThanOrEqualTo(root.get("price"), card.getMaxPrice()));
			}
			if (card.getProductCategory() != null) {
				predicates.add(
						criteriaBuilder.equal(root.get("productCategory"),
								card.getProductCategory()));
			}
			if (card.getArea() != null) {
				predicates.add(criteriaBuilder.equal(root.get("area"), card.getArea()));
			}
			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		};

		Page<Product> products = productRepository.findAll(specification, pageable);
		List<ProductPreviewDto> productPreviewDtos = products.stream()
				.map(product -> {
							SpecificAreaDto areaDto = areaMapper.emdAreaToSpecificAreaDto(card.getArea());
							SpecificProductCategoryDto categoryDto = productMapper
									.convertToSpecificProductCategoryDtoRecursive(
											card.getProductCategory());
							List<ProductKeywordDto> productKeywordDtos = productMapper.toProductKeywordDto(
									product.getProductKeywords());
							return productMapper.toProductPreviewDto(product, areaDto, categoryDto,
									productKeywordDtos);
						}
				).collect(Collectors.toList());

		return productMapper.toProductListResponseDto(productPreviewDtos,
				products.getTotalElements());
	}
}

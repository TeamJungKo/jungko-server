package com.jungko.jungko_server.card.service;

import com.jungko.jungko_server.area.domain.EmdArea;
import com.jungko.jungko_server.area.infrastructure.EmdAreaRepository;
import com.jungko.jungko_server.card.domain.Card;
import com.jungko.jungko_server.card.dto.CardPreviewDto;
import com.jungko.jungko_server.card.dto.request.CardCreateRequestDto;
import com.jungko.jungko_server.card.infrastructure.CardRepository;
import com.jungko.jungko_server.mapper.AreaMapper;
import com.jungko.jungko_server.mapper.MemberMapper;
import com.jungko.jungko_server.mapper.ProductMapper;
import com.jungko.jungko_server.member.domain.Member;
import com.jungko.jungko_server.member.infrastructure.MemberRepository;
import com.jungko.jungko_server.product.domain.ProductCategory;
import com.jungko.jungko_server.product.infrastructure.ProductCategoryRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

	public CardPreviewDto createCard(Long memberId, CardCreateRequestDto dto) {
		log.info("Called createCard memberId: {}, dto: {}", memberId, dto);

		Member loginMember = memberRepository.findById(memberId).orElseThrow(
				() -> new HttpClientErrorException(
						HttpStatus.NOT_FOUND,
						"해당 회원이 존재하지 않습니다. id=" + memberId));
		ProductCategory productCategory = productCategoryRepository.findByIdAndFetchParentEagerly(
						dto.getCategoryId())
				.orElseThrow(
						() -> new HttpClientErrorException(
								HttpStatus.NOT_FOUND,
								"해당 상품 카테고리가 존재하지 않습니다. id=" + dto.getCategoryId()));
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

		return CardPreviewDto.builder()
				.cardId(savedCard.getId())
				.title(savedCard.getTitle())
				.keyword(savedCard.getKeyword())
				.minPrice(savedCard.getMinPrice())
				.maxPrice(savedCard.getMaxPrice())
				.scope(savedCard.getScope())
				.createdAt(savedCard.getCreatedAt())
				.author(memberMapper.toMemberProfileDto(savedCard.getMember(),
						savedCard.getMember().getProfileImageUrl()))
				.area(areaMapper.emdAreaToSpecificAreaDto(savedCard.getArea()))
				.category(productMapper.convertToSpecificProductCategoryDtoRecursive(
						savedCard.getProductCategory()))
				.build();
	}
}

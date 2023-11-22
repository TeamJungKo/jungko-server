package com.jungko.jungko_server.card.service;

import com.jungko.jungko_server.area.dto.SpecificAreaDto;
import com.jungko.jungko_server.card.domain.Card;
import com.jungko.jungko_server.card.domain.InterestedCard;
import com.jungko.jungko_server.card.dto.CardPreviewDto;
import com.jungko.jungko_server.card.dto.response.CardListResponseDto;
import com.jungko.jungko_server.card.infrastructure.CardRepository;
import com.jungko.jungko_server.card.infrastructure.InterestedCardRepository;
import com.jungko.jungko_server.mapper.AreaMapper;
import com.jungko.jungko_server.mapper.CardMapper;
import com.jungko.jungko_server.mapper.MemberMapper;
import com.jungko.jungko_server.mapper.ProductMapper;
import com.jungko.jungko_server.member.domain.Member;
import com.jungko.jungko_server.member.dto.MemberProfileDto;
import com.jungko.jungko_server.member.infrastructure.MemberRepository;
import com.jungko.jungko_server.product.dto.SpecificProductCategoryDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class InterestedCardService {

	private final InterestedCardRepository interestedCardRepository;
	private final CardRepository cardRepository;
	private final MemberRepository memberRepository;
	private final MemberMapper memberMapper;
	private final AreaMapper areaMapper;
	private final ProductMapper productMapper;
	private final CardMapper cardMapper;

	public void likeCard(Long memberId, Long cardId) {
		log.info("Called likeCard memberId: {}, cardId: {}", memberId, cardId);
		Member loginMember = memberRepository.findById(memberId).orElseThrow(
				() -> new HttpClientErrorException(
						HttpStatus.NOT_FOUND,
						"해당 회원이 존재하지 않습니다. id=" + memberId));
		Card card = cardRepository.findById(cardId).orElseThrow(
				() -> new HttpClientErrorException(
						HttpStatus.NOT_FOUND,
						"해당 카드가 존재하지 않습니다. id=" + cardId));
		if (card.isOwner(loginMember)) {
			throw new HttpClientErrorException(
					HttpStatus.FORBIDDEN,
					"자신이 만든 카드는 관심 카드로 등록할 수 없습니다. cardId=" + cardId + ", memberId=" + memberId);
		}
		interestedCardRepository
				.findByMemberIdAndCardId(memberId, cardId)
				.orElseGet(() -> {
					InterestedCard createdInterestedCard = InterestedCard.createInterestedCard(
							loginMember, card);
					return interestedCardRepository.save(createdInterestedCard);
				});
	}

	public void unlikeCard(Long memberId, Long cardId) {
		log.info("Called unlikeCard memberId: {}, cardId: {}", memberId, cardId);
		memberRepository.findById(memberId).orElseThrow(
				() -> new HttpClientErrorException(
						HttpStatus.NOT_FOUND,
						"해당 회원이 존재하지 않습니다. id=" + memberId));
		interestedCardRepository
				.findByMemberIdAndCardId(memberId, cardId)
				.ifPresentOrElse(
						interestedCardRepository::delete,
						() -> {
							throw new HttpClientErrorException(
									HttpStatus.NOT_FOUND,
									"관심 카드가 존재하지 않습니다. cardId=" + cardId + ", memberId="
											+ memberId);
						});
	}

	public CardListResponseDto getLikedCards(Long memberId, Long targetMemberId,
			Pageable pageable, Long categoryId) {
		log.info(
				"Called getLikedCards memberId: {}, targetMemberId: {}, pageable: {}, categoryId: {}",
				memberId, targetMemberId, pageable, categoryId);
		memberRepository.findById(memberId).orElseThrow(
				() -> new HttpClientErrorException(
						HttpStatus.NOT_FOUND,
						"해당 회원이 존재하지 않습니다. id=" + memberId));

		memberRepository.findById(targetMemberId).orElseThrow(
				() -> new HttpClientErrorException(
						HttpStatus.NOT_FOUND,
						"해당 회원이 존재하지 않습니다. id=" + targetMemberId));
		Page<Card> cards = cardRepository.findAllByInterestedCardsMemberId(targetMemberId,
				pageable);
		Page<Card> filteredCards = cards.stream()
				.filter(card -> categoryId == null || card.getProductCategory().getId()
						.equals(categoryId))
				.collect(Collectors.collectingAndThen(Collectors.toList(),
						list -> new PageImpl<>(list, pageable, cards.getTotalElements())));

		List<CardPreviewDto> cardPreviewDtos = filteredCards.stream()
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
}

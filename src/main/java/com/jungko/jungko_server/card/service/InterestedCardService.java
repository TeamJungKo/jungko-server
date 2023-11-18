package com.jungko.jungko_server.card.service;

import com.jungko.jungko_server.card.domain.Card;
import com.jungko.jungko_server.card.domain.InterestedCard;
import com.jungko.jungko_server.card.infrastructure.CardRepository;
import com.jungko.jungko_server.card.infrastructure.InterestedCardRepository;
import com.jungko.jungko_server.member.domain.Member;
import com.jungko.jungko_server.member.infrastructure.MemberRepository;
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
public class InterestedCardService {

	private final InterestedCardRepository interestedCardRepository;
	private final CardRepository cardRepository;
	private final MemberRepository memberRepository;

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
}

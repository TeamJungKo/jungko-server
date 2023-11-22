package com.jungko.jungko_server.card.infrastructure;

import com.jungko.jungko_server.card.domain.Card;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface CardRepository extends JpaRepository<Card, Long> {

	Page<Card> findAllByMemberId(Long memberId, Pageable pageable);

	@Query("SELECT c FROM Card c LEFT JOIN c.interestedCards ic "
			+ "GROUP BY c.id ORDER BY COUNT(ic) DESC")
	Page<Card> findAllByInterestedCardsCount(Pageable pageable);

	@Query("SELECT c FROM Card c JOIN c.interestedCards ic "
			+ "WHERE ic.member.id = :memberId")
	Page<Card> findAllByInterestedCardsMemberId(Long memberId, Pageable pageable);
}

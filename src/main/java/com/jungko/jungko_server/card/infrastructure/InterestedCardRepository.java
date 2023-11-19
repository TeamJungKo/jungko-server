package com.jungko.jungko_server.card.infrastructure;

import com.jungko.jungko_server.card.domain.InterestedCard;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface InterestedCardRepository extends JpaRepository<InterestedCard, Long> {

	@Query("select ic from InterestedCard ic "
			+ "where ic.member.id = :memberId "
			+ "and ic.card.id = :cardId")
	Optional<InterestedCard> findByMemberIdAndCardId(Long memberId, Long cardId);
}

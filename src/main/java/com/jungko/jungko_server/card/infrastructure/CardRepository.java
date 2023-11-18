package com.jungko.jungko_server.card.infrastructure;

import com.jungko.jungko_server.card.domain.Card;
import com.jungko.jungko_server.member.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CardRepository extends JpaRepository<Card, Long> {

	Page<Card> findAllByMemberId(Long memberId, Pageable pageable);
}

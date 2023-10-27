package com.jungko.jungko_server.card.infrastructure;

import com.jungko.jungko_server.card.domain.InterestedCard;
import org.springframework.data.jpa.repository.JpaRepository;


public interface InterestedCardRepository extends JpaRepository<InterestedCard, Long> {
}

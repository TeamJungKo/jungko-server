package com.jungko.jungko_server.card.infrastructure;

import com.jungko.jungko_server.card.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CardRepository extends JpaRepository<Card, Long> {
}

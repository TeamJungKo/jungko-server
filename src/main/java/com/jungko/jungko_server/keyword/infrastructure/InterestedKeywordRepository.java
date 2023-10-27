package com.jungko.jungko_server.keyword.infrastructure;

import com.jungko.jungko_server.keyword.domain.InterestedKeyword;
import org.springframework.data.jpa.repository.JpaRepository;


public interface InterestedKeywordRepository extends JpaRepository<InterestedKeyword, Long> {
}

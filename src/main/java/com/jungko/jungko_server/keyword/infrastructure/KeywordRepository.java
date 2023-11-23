package com.jungko.jungko_server.keyword.infrastructure;

import com.jungko.jungko_server.keyword.domain.Keyword;
import com.jungko.jungko_server.keyword.domain.InterestedKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {
    //List<Keyword> findBy(String keyword);
    // Optional<Keyword> findById(String email);
    // }
}
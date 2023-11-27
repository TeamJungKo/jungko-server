package com.jungko.jungko_server.keyword.infrastructure;

import com.jungko.jungko_server.keyword.domain.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {
    @Query("select ic from Keyword ic "
            + "where ic.member.id = :memberId "
            + "and ic.keyword.id = :keywordId")
    Optional<Keyword> findByMemberIdAndKeywordId(Long memberId, Long keywordId);
}

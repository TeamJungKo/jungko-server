package com.jungko.jungko_server.keyword.infrastructure;

import com.jungko.jungko_server.keyword.domain.InterestedKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface InterestedKeywordRepository extends JpaRepository<InterestedKeyword, Long> {

    @Query("select ic from InterestedKeyword ic "
            + "where ic.member.id = :memberId "
            + "and ic.keyword.id = :keywordId")
    Optional<InterestedKeyword> findByMemberIdAndKeywordId(Long memberId, Long keywordId);
}

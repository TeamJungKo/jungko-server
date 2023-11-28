package com.jungko.jungko_server.keyword.service;
import com.jungko.jungko_server.keyword.domain.Keyword;
import com.jungko.jungko_server.keyword.dto.KeywordDto;
import com.jungko.jungko_server.keyword.dto.response.KeywordListResponseDto;
import com.jungko.jungko_server.keyword.infrastructure.KeywordRepository;
import com.jungko.jungko_server.member.domain.Member;
import com.jungko.jungko_server.member.dto.MemberProfileDto;
import com.jungko.jungko_server.member.infrastructure.MemberRepository;
import com.jungko.jungko_server.product.infrastructure.ProductCategoryRepository;
import com.jungko.jungko_server.mapper.MemberMapper;
import com.jungko.jungko_server.mapper.KeywordMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
public class KeywordService {
    private final KeywordRepository keywordRepository;
    private final MemberRepository memberRepository;
    private final KeywordMapper keywordMapper;
    private final MemberMapper memberMapper;
    public void createKeyword(Long memberId, List<String> keywords) {
        log.info("Called createKeyword memberId: {}, keywords: {}", memberId, keywords);
        Member loginMember = memberRepository.findById(memberId).orElseThrow(
                () -> new HttpClientErrorException(
                        HttpStatus.NOT_FOUND, "해당 회원이 존재하지 않습니다. id=" + memberId));

        List<Keyword> newKeywords = Keyword.createKeyword(loginMember, keywords);
        keywordRepository.saveAll(newKeywords);
    }

    //memberId에 해당하는 member keywordId를 지우는거
    public void deleteKeyword(Long memberId, Long keywordId) {
        log.info("Called deleteKeyword memberId: {}, keywordId: {}", memberId, keywordId);
        Member loginMember = memberRepository.findById(memberId).orElseThrow(
                () -> new HttpClientErrorException(HttpStatus.NOT_FOUND,"해당 회원이 존재하지 않습니다. id=" + memberId));
        Keyword keyword = keywordRepository.findById(keywordId).orElseThrow(
                () -> new HttpClientErrorException(HttpStatus.NOT_FOUND,"해당 키워드가 존재하지 않습니다. id=" + keywordId));
        System.out.println(keyword);
        if (!keyword.isOwner(loginMember)) {
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN,"해당 키워드의 소유자가 아닙니다. keywordId=" + keywordId + ", memberId=" + memberId);
        }
        keywordRepository.delete(keyword);
    }

    public KeywordListResponseDto getKeywordsByMemberId(Long memberId) {
        log.info("Called getMemberKeywords memberId: {}", memberId);
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "해당 회원이 존재하지 않습니다. id=" + memberId));

        List<KeywordDto> keywordDtos = member.getInterestedKeywords().stream()
                .map(keyword -> keywordMapper.toKeywordDto(keyword, memberMapper.toMemberProfileDto(member, member.getProfileImageUrl())
                ))
                .collect(Collectors.toList());

        System.out.println(keywordDtos);
        return keywordMapper.toKeywordListResponseDto(keywordDtos, keywordDtos.size());
    }
}


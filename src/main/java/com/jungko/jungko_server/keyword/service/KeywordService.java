package com.jungko.jungko_server.keyword.service;
import com.jungko.jungko_server.keyword.domain.Keyword;
import com.jungko.jungko_server.keyword.dto.request.KeywordRequestDto;
import com.jungko.jungko_server.keyword.dto.KeywordDto;
import com.jungko.jungko_server.keyword.dto.response.KeywordListResponseDto;
import com.jungko.jungko_server.keyword.infrastructure.KeywordRepository;
import com.jungko.jungko_server.member.domain.Member;
import com.jungko.jungko_server.member.dto.MemberProfileDto;
import com.jungko.jungko_server.member.infrastructure.MemberRepository;
import com.jungko.jungko_server.product.domain.ProductCategory;
import com.jungko.jungko_server.product.infrastructure.ProductCategoryRepository;
import com.jungko.jungko_server.mapper.MemberMapper;
import com.jungko.jungko_server.mapper.KeywordMapper;import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.criteria.Predicate;
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
    private final ProductCategoryRepository productCategoryRepository;
    private final KeywordMapper keywordMapper;
    private final MemberMapper memberMapper;
    public KeywordDto createKeyword(Long memberId, KeywordRequestDto dto) {
        log.info("Called createKeyword memberId: {}, dto: {}", memberId, dto);
        Member loginMember = memberRepository.findById(memberId).orElseThrow(
                () -> new HttpClientErrorException(HttpStatus.NOT_FOUND,"해당 회원이 존재하지 않습니다. id=" + memberId));
        Keyword createdKeyword = Keyword.createKeyword(dto.getKeywordId(),dto.getKeyword());
        createdKeyword.setOwner(loginMember);Keyword savedKeyword = KeywordRepository.save(createdKeyword);
        MemberProfileDto author = memberMapper.toMemberProfileDto(savedKeyword.getMember(),savedKeyword.getMember().getProfileImageUrl());
        return keywordMapper.toKeywordDto(savedKeyword, author);
    }

    //memberId에 해당하는 membere의 keywordId를 지우는거
    public void deleteKeyword(Long memberId, Long keywordId) {
        log.info("Called deleteKeword memberId: {}, keywordId: {}", memberId, keywordId);
        Member loginMember = memberRepository.findById(memberId).orElseThrow(
                () -> new HttpClientErrorException(HttpStatus.NOT_FOUND,"해당 회원이 존재하지 않습니다. id=" + memberId));
        Keyword keyword = KeywordRepository.findById(keywordId).orElseThrow(
                () -> new HttpClientErrorException(HttpStatus.NOT_FOUND,"해당 키워드가 존재하지 않습니다. id=" + keywordId));
        System.out.println(keyword);
        if (!keyword.isOwner(loginMember)) {
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN,"해당 키워드의 소유자가 아닙니다. keywordId=" + keywordId + ", memberId=" + memberId);
        }
        KeywordRepository.delete(keyword);
    }


    /*
    public KeywordListResponseDto getMemberKeywords(Long memberId) {
        log.info("Called getMemberKeywords memberId: {}", memberId);
        Member loginMember = memberRepository.findById(memberId).orElseThrow(
                () -> new HttpClientErrorException(HttpStatus.NOT_FOUND,"해당 회원이 존재하지 않습니다. id=" + memberId));

        List<KeywordDto> keywordDtos = filteredCards.stream().filter(card -> categoryId == null || card.getProductCategory().getId().equals(categoryId)).map(card -> {
            MemberProfileDto author = memberMapper.toMemberProfileDto(card.getMember(),card.getMember().getProfileImageUrl());

            return keywordMapper.toKeywordDto(keyword, author);}).collect(Collectors.toList());
        return keywordMapper.toKeywordListResponseDto(cardPreviewDtos, filteredCards.getTotalElements());
    }
     */
}


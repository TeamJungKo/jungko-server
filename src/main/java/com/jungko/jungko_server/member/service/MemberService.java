package com.jungko.jungko_server.member.service;

import com.jungko.jungko_server.member.domain.Member;
import com.jungko.jungko_server.member.dto.MemberDTO;
import com.jungko.jungko_server.member.infrastructure.MemberRepository;
import com.jungko.jungko_server.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<MemberDTO> findAll() {
        final List<Member> members = memberRepository.findAll(Sort.by("id"));
        return members.stream()
                .map(member -> mapToDTO(member, new MemberDTO()))
                .toList();
    }

    public MemberDTO get(final Long id) {
        return memberRepository.findById(id)
                .map(member -> mapToDTO(member, new MemberDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final MemberDTO memberDTO) {
        final Member member = new Member();
        mapToEntity(memberDTO, member);
        return memberRepository.save(member).getId();
    }

    public void update(final Long id, final MemberDTO memberDTO) {
        final Member member = memberRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(memberDTO, member);
        memberRepository.save(member);
    }

    public void delete(final Long id) {
        memberRepository.deleteById(id);
    }

    private MemberDTO mapToDTO(final Member member, final MemberDTO memberDTO) {
        memberDTO.setId(member.getId());
        memberDTO.setEmail(member.getEmail());
        memberDTO.setProfileImageUrl(member.getProfileImageUrl());
        memberDTO.setNickname(member.getNickname());
        memberDTO.setNotificationAgreement(member.getNotificationAgreement());
        memberDTO.setOauthType(member.getOauthType());
        memberDTO.setOauthId(member.getOauthId());
        memberDTO.setCreatedAt(member.getCreatedAt());
        memberDTO.setDeletedAt(member.getDeletedAt());
        return memberDTO;
    }

    private Member mapToEntity(final MemberDTO memberDTO, final Member member) {
        member.setEmail(memberDTO.getEmail());
        member.setProfileImageUrl(memberDTO.getProfileImageUrl());
        member.setNickname(memberDTO.getNickname());
        member.setNotificationAgreement(memberDTO.getNotificationAgreement());
        member.setOauthType(memberDTO.getOauthType());
        member.setOauthId(memberDTO.getOauthId());
        member.setCreatedAt(memberDTO.getCreatedAt());
        member.setDeletedAt(memberDTO.getDeletedAt());
        return member;
    }

    public boolean emailExists(final String email) {
        return memberRepository.existsByEmailIgnoreCase(email);
    }

    public boolean nicknameExists(final String nickname) {
        return memberRepository.existsByNicknameIgnoreCase(nickname);
    }

}

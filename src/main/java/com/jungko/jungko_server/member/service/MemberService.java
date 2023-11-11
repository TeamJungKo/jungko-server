package com.jungko.jungko_server.member.service;

import com.jungko.jungko_server.mapper.MemberMapper;
import com.jungko.jungko_server.member.domain.Member;
import com.jungko.jungko_server.member.dto.MemberProfileDto;
import com.jungko.jungko_server.member.infrastructure.MemberRepository;
import com.jungko.jungko_server.util.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

	private final MemberRepository memberRepository;
	private final MemberMapper memberMapper;

	public MemberProfileDto getMyProfile(Long loginMemberId) {
		log.info("Called getMyProfile member: {}", loginMemberId);

		Member member = memberRepository.findById(loginMemberId).orElseThrow(
				() -> new NotFoundException("해당 회원이 존재하지 않습니다. id=" + loginMemberId));
		return memberMapper.toMemberProfileDto(member, member.getProfileImageUrl());
	}

	public MemberProfileDto getMemberProfile(Long memberId) {
		log.info("Called getMemberProfile member: {}", memberId);

		Member member = memberRepository.findById(memberId).orElseThrow(
				() -> new NotFoundException("해당 회원이 존재하지 않습니다. id=" + memberId));
		return memberMapper.toMemberProfileDto(member, member.getProfileImageUrl());
	}
}

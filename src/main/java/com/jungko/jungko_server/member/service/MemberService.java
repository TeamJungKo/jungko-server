package com.jungko.jungko_server.member.service;

import com.jungko.jungko_server.mapper.MemberMapper;
import com.jungko.jungko_server.member.domain.Member;
import com.jungko.jungko_server.member.dto.MemberProfileDto;
import com.jungko.jungko_server.member.dto.request.MemberProfileUpdateRequestDto;
import com.jungko.jungko_server.member.infrastructure.MemberRepository;
import com.jungko.jungko_server.util.ImageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MemberService {

	private final MemberRepository memberRepository;
	private final MemberMapper memberMapper;
	private final ImageUtil imageUtil;
	@Value("${jungko.images.path.profile}")
	private String profileImagePath;

	public MemberProfileDto getMyProfile(Long loginMemberId) {
		log.info("Called getMyProfile member: {}", loginMemberId);

		Member member = memberRepository.findById(loginMemberId).orElseThrow(
				() -> new HttpClientErrorException(
						HttpStatus.NOT_FOUND,
						"해당 회원이 존재하지 않습니다. id=" + loginMemberId));
		return memberMapper.toMemberProfileDto(member, member.getProfileImageUrl());
	}

	public MemberProfileDto getMemberProfile(Long memberId) {
		log.info("Called getMemberProfile member: {}", memberId);

		Member member = memberRepository.findById(memberId).orElseThrow(
				() -> new HttpClientErrorException(
						HttpStatus.NOT_FOUND,
						"해당 회원이 존재하지 않습니다. id=" + memberId));
		return memberMapper.toMemberProfileDto(member, member.getProfileImageUrl());
	}

	public MemberProfileDto updateMemberProfile(Long memberId, MemberProfileUpdateRequestDto dto) {
		log.info("Called updateMemberProfile member: {}, dto: {}", memberId, dto);

		Member member = memberRepository.findById(memberId).orElseThrow(
				() -> new HttpClientErrorException(
						HttpStatus.NOT_FOUND,
						"해당 회원이 존재하지 않습니다. id=" + memberId));

		String filePath = imageUtil.uploadFile(dto.getImageData(), profileImagePath);
		String profileImageUrl = imageUtil.getImageUrl(filePath);
		member.updateProfile(dto.getNickname(), dto.getEmail(), profileImageUrl);
		memberRepository.save(member);
		return memberMapper.toMemberProfileDto(member, member.getProfileImageUrl());
	}
}

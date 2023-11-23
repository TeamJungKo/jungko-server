package com.jungko.jungko_server.notification.service;

import com.jungko.jungko_server.member.domain.Member;
import com.jungko.jungko_server.member.infrastructure.MemberRepository;
import com.jungko.jungko_server.notification.domain.Notification;
import com.jungko.jungko_server.notification.dto.KeywordNoticeDto;
import com.jungko.jungko_server.notification.dto.response.KeywordNoticeListResponseDto;
import com.jungko.jungko_server.notification.infrastructure.NotificationRepository;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import java.util.List;


@Service
@RequiredArgsConstructor
public class NotificationService {

	private final NotificationRepository notificationRepository;
	private final MemberRepository memberRepository;


	public KeywordNoticeListResponseDto getNoticesByKeyword(Long memberId,
			PageRequest pageRequest) {

		Member loginMember = memberRepository.findById(memberId).orElseThrow(
				() -> new HttpClientErrorException(
						HttpStatus.NOT_FOUND,
						"해당 회원이 존재하지 않습니다. id=" + memberId));

		// Repository에 KEYWORD타입인 notice만 가져오도록 Query문 작성 필요
		Page<Notification> notices = notificationRepository
				.findAllByMemberId(memberId, pageRequest);

		List<KeywordNoticeDto> keywordNoticeDtos;

		return null;
	}
}

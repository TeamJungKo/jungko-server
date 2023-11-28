package com.jungko.jungko_server.notification.service;

import com.jungko.jungko_server.mapper.NotificationMapper;
import com.jungko.jungko_server.member.domain.Member;
import com.jungko.jungko_server.member.infrastructure.MemberRepository;
import com.jungko.jungko_server.notification.domain.Notification;
import com.jungko.jungko_server.notification.dto.KeywordNoticeDto;
import com.jungko.jungko_server.notification.dto.request.DeviceTokenRequestDto;
import com.jungko.jungko_server.notification.dto.response.KeywordNoticeListResponseDto;
import com.jungko.jungko_server.notification.infrastructure.NotificationRepository;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class NotificationService {

	private final NotificationMapper notificationMapper;
	private final NotificationRepository notificationRepository;
	private final MemberRepository memberRepository;


	public KeywordNoticeListResponseDto getNoticesByKeyword(Long memberId,
			PageRequest pageRequest) {

		Member loginMember = memberRepository.findById(memberId).orElseThrow(
				() -> new HttpClientErrorException(
						HttpStatus.NOT_FOUND,
						"해당 회원이 존재하지 않습니다. id=" + memberId));

		Page<Notification> notices = notificationRepository
				.findAllByMemberId(loginMember.getId(), pageRequest);

		List<KeywordNoticeDto> keywordNoticeDtos = notices.stream()
				.map(notificationMapper::toKeywordNoticeDto)
				.collect(Collectors.toList());

		return notificationMapper.toKeywordNoticeListResponseDto(keywordNoticeDtos,
				(long) keywordNoticeDtos.size());
	}

	public void deleteNotices(Long memberId, List<Long> noticeIds) {

		Member loginMember = memberRepository.findById(memberId).orElseThrow(
				() -> new HttpClientErrorException(
						HttpStatus.NOT_FOUND,
						"해당 회원이 존재하지 않습니다. id=" + memberId));

		List<Notification> notices = notificationRepository.findAllByIdAndMemberId(noticeIds,
				loginMember.getId());

		notificationRepository.deleteAll(notices);
	}

	public void toggleAgreement(Long memberId, DeviceTokenRequestDto dto) {
		log.info("Called toggleAgreement memberId: {}, dto: {}", memberId, dto);

		Member loginMember = memberRepository.findById(memberId).orElseThrow(
				() -> new HttpClientErrorException(
						HttpStatus.NOT_FOUND,
						"해당 회원이 존재하지 않습니다. id=" + memberId));

		boolean temp = loginMember.getNotificationAgreement();
		// 알림을 이미 동의중인 경우에는 알림을 거부하고, 디바이스 토큰 정보를 null로 초기화한다.
		// 알림을 이미 동의중이지 않은 경우 알림을 허용으로 바꾸고, dto에 담긴 디바이스 토큰 정보를 저장한다.
		if (temp) {
			loginMember.setNotificationAgreement(false);
			loginMember.setDeviceToken(null);
		} else {
			if (dto == null || dto.getDeviceToken() == null || dto.getDeviceToken().isEmpty()) {
				throw new HttpClientErrorException(
						HttpStatus.BAD_REQUEST,
						"디바이스 토큰 정보가 없습니다.");
			}
			loginMember.setNotificationAgreement(true);
			loginMember.setDeviceToken(dto.getDeviceToken());
		}
		memberRepository.save(loginMember);
	}
}

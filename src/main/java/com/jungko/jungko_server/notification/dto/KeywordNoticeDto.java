package com.jungko.jungko_server.notification.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

import com.jungko.jungko_server.notification.domain.NoticeType;

@AllArgsConstructor
@Getter
@ToString
@Builder
@Schema(description = "키워드 알림에 대한 DTO")
public class KeywordNoticeDto {

	@Schema(description = "notice 고유 번호", example = "1")
	private final Long noticeId;

	@Schema(description = "notice 제목", example = "[아이유] 서울시 중구 장충동 2가")
	private final String title;

	@Schema(description = "notice 내용", example = "아이유 검은 바지 두둥!!")
	private final String content;

	@Schema(description = "상품 고유 번호", example = "123")
	private final Long productId;

	@Schema(description = "알림 생성 시간", example = "2023-11-04T12:34:56Z")
	private final LocalDateTime createdAt;

	@Schema(description = "알림 읽음 여부", example = "false")
	private final Boolean isRead;
}

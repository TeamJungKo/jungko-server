package com.jungko.jungko_server.mapper;

import com.jungko.jungko_server.notification.domain.Notification;
import com.jungko.jungko_server.notification.dto.KeywordNoticeDto;
import com.jungko.jungko_server.notification.dto.response.KeywordNoticeListResponseDto;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

	List<KeywordNoticeDto> toKeywordNoticeDtos(List<Notification> content);

	KeywordNoticeListResponseDto toKeywordNoticeListResponseDto(
			List<KeywordNoticeDto> keywordNoticeDtos, Long totalElements);
}

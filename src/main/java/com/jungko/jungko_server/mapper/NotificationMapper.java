package com.jungko.jungko_server.mapper;

import com.jungko.jungko_server.notification.domain.Notification;
import com.jungko.jungko_server.notification.dto.KeywordNoticeDto;
import com.jungko.jungko_server.notification.dto.response.KeywordNoticeListResponseDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

	@Mapping(target = "noticeId", source = "id")
	KeywordNoticeDto toKeywordNoticeDto(Notification notification);

	@Mapping(target = "keywordNotices", source = "keywordNoticeDtos")
	@Mapping(target = "totalResources", source = "totalElements")
	KeywordNoticeListResponseDto toKeywordNoticeListResponseDto(
			List<KeywordNoticeDto> keywordNoticeDtos, Long totalElements);

}

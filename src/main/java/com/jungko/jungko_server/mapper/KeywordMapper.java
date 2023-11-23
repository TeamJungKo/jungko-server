package com.jungko.jungko_server.mapper;
import com.jungko.jungko_server.keyword.domain.Keyword;
import com.jungko.jungko_server.keyword.dto.KeywordDto;
import com.jungko.jungko_server.keyword.dto.response.KeywordListResponseDto;
import com.jungko.jungko_server.member.dto.MemberProfileDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface KeywordMapper {
	KeywordMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(KeywordMapper.class);
	@Mapping(target = "kewordId", source = "keword.id")
	@Mapping(target = "author", source = "author")
	KeywordDto toKeywordDto(Keyword keyword, MemberProfileDto author);
	@Mapping(target = "kewords", source = "cardPreviewDtos")
	KeywordListResponseDto toKeywordListResponseDto(List<KeywordDto> KeywordDtos, long totalResources);
}
package com.jungko.jungko_server.mapper;
import com.jungko.jungko_server.keyword.domain.Keyword;
import com.jungko.jungko_server.keyword.dto.KeywordDto;
import com.jungko.jungko_server.keyword.dto.response.KeywordListResponseDto;
import com.jungko.jungko_server.member.domain.Member;
import com.jungko.jungko_server.member.dto.MemberProfileDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface KeywordMapper {
	KeywordMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(KeywordMapper.class);
	@Mapping(target = "keywordId", source = "keyword.id")
	// @Mapping(target = "author", source = "author.memberId")
	KeywordDto toKeywordDto(Keyword keyword, MemberProfileDto author);

	@Mapping(target = "keywordList", source = "keywordDtos")
	KeywordListResponseDto toKeywordListResponseDto(List<KeywordDto> keywordDtos, long totalResources);
}
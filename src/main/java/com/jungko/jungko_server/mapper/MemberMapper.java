package com.jungko.jungko_server.mapper;

import com.jungko.jungko_server.member.domain.Member;
import com.jungko.jungko_server.member.dto.MemberProfileDto;
import com.jungko.jungko_server.member.dto.response.MemberProfileResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MemberMapper {

	MemberMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(MemberMapper.class);

	@Mapping(source = "member.id", target = "memberId")
	MemberProfileDto toMemberProfileDto(Member member, String imageUrl);

	@Mapping(source = "memberProfileDto.memberId", target = "memberId")
	MemberProfileResponseDto toMemberProfileResponseDto(MemberProfileDto memberProfileDto);
}

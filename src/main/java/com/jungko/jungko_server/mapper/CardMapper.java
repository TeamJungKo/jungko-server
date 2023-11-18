package com.jungko.jungko_server.mapper;

import com.jungko.jungko_server.area.dto.SpecificAreaDto;
import com.jungko.jungko_server.card.domain.Card;
import com.jungko.jungko_server.card.dto.CardPreviewDto;
import com.jungko.jungko_server.card.dto.response.CardListResponseDto;
import com.jungko.jungko_server.member.dto.MemberProfileDto;
import com.jungko.jungko_server.product.dto.SpecificProductCategoryDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CardMapper {

	CardMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(CardMapper.class);

	@Mapping(target = "cardId", source = "card.id")
	@Mapping(target = "author", source = "author")
	@Mapping(target = "area", source = "areaDto")
	@Mapping(target = "category", source = "categoryDto")
	CardPreviewDto toCardPreviewDto(Card card, MemberProfileDto author, SpecificAreaDto areaDto,
			SpecificProductCategoryDto categoryDto);

	@Mapping(target = "cards", source = "cardPreviewDtos")
	CardListResponseDto toCardListResponseDto(List<CardPreviewDto> cardPreviewDtos,
			long totalResources);
}

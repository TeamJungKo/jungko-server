package com.jungko.jungko_server.mapper;

import com.jungko.jungko_server.area.domain.EmdArea;
import com.jungko.jungko_server.area.domain.SidoArea;
import com.jungko.jungko_server.area.domain.SiggArea;
import com.jungko.jungko_server.area.dto.AreaDto;
import com.jungko.jungko_server.area.dto.EmdDto;
import com.jungko.jungko_server.area.dto.SidoDto;
import com.jungko.jungko_server.area.dto.SiggDto;
import com.jungko.jungko_server.area.dto.response.AreaListResponseDto;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AreaMapper {

	AreaMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(AreaMapper.class);

	@Mapping(source = "siggAreas", target = "sigg")
	SidoDto sidoAreaToSidoDto(SidoArea sidoArea);

	@Mapping(source = "emdAreas", target = "emd")
	SiggDto siggAreaToSiggDto(SiggArea siggArea);

	EmdDto emdAreaToEmdDto(EmdArea emdArea);

	default AreaListResponseDto toAreaListResponseDto(List<SidoArea> sidoAreas) {
		List<SidoDto> sidoDtos = sidoAreas.stream()
				.map(this::sidoAreaToSidoDto)
				.collect(Collectors.toList());

		return AreaListResponseDto.builder()
				.areas(Collections.singletonList(
						AreaDto.builder()
								.sido(sidoDtos)
								.build()))
				.build();
	}
}

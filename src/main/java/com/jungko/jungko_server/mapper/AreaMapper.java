package com.jungko.jungko_server.mapper;

import com.jungko.jungko_server.area.domain.EmdArea;
import com.jungko.jungko_server.area.domain.SidoArea;
import com.jungko.jungko_server.area.domain.SiggArea;
import com.jungko.jungko_server.area.dto.AreaDto;
import com.jungko.jungko_server.area.dto.EmdDto;
import com.jungko.jungko_server.area.dto.SidoDto;
import com.jungko.jungko_server.area.dto.SiggDto;
import com.jungko.jungko_server.area.dto.SpecificAreaDto;
import com.jungko.jungko_server.area.dto.SpecificEmdDto;
import com.jungko.jungko_server.area.dto.SpecificSidoDto;
import com.jungko.jungko_server.area.dto.SpecificSiggDto;
import com.jungko.jungko_server.area.dto.response.AreaListResponseDto;
import java.util.Collections;
import java.util.Set;
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

	default SpecificAreaDto emdAreaToSpecificAreaDto(EmdArea emdArea) {
		if (emdArea == null || emdArea.getSiggArea() == null
				|| emdArea.getSiggArea().getSidoArea() == null) {
			return null;
		}

		return SpecificAreaDto.builder()
				.sido(SpecificSidoDto.builder()
						.name(emdArea.getSiggArea().getSidoArea().getName())
						.code(emdArea.getSiggArea().getSidoArea().getCode())
						.sigg(SpecificSiggDto.builder()
								.name(emdArea.getSiggArea().getName())
								.code(emdArea.getSiggArea().getCode())
								.emd(SpecificEmdDto.builder()
										.name(emdArea.getName())
										.code(emdArea.getCode())
										.build())
								.build())
						.build())
				.build();
	}


	default AreaListResponseDto toAreaListResponseDto(Set<SidoArea> sidoAreas) {
		Set<SidoDto> sidoDtos = sidoAreas.stream()
				.map(this::sidoAreaToSidoDto)
				.collect(Collectors.toSet());

		return AreaListResponseDto.builder()
				.areas(Collections.singletonList(
						AreaDto.builder()
								.sido(sidoDtos)
								.build()))
				.build();
	}
}

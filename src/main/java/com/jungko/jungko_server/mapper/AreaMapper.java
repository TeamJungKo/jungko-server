package com.jungko.jungko_server.mapper;

import com.jungko.jungko_server.area.domain.EmdArea;
import com.jungko.jungko_server.area.domain.SidoArea;
import com.jungko.jungko_server.area.domain.SiggArea;
import com.jungko.jungko_server.area.dto.AreaDto;
import com.jungko.jungko_server.area.dto.EmdDto;
import com.jungko.jungko_server.area.dto.SidoDto;
import com.jungko.jungko_server.area.dto.SiggDto;
import com.jungko.jungko_server.area.dto.response.AreaListResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.awt.geom.Area;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AreaMapper {

    AreaMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(AreaMapper.class);

    @Mapping(source = "emd.admCode", target = "code")
    EmdDto toEmdDto(EmdArea emd);

    @Mapping(source = "sigg.admCode", target = "code")
    SiggDto toSiggDto(SiggArea sigg);

    @Mapping(source = "sido.admCode", target = "code")
    SidoDto toSidoDto(SidoArea sido);

    @Mapping(source = "sido", target = "sido")
    AreaDto toAreaDto(SidoArea sido);

}

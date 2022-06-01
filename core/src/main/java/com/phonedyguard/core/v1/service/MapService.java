package com.phonedyguard.core.v1.service;

import com.phonedyguard.core.entity.MapEntity;
import com.phonedyguard.core.entity.MapSafeEntity;
import com.phonedyguard.core.v1.dto.Response;
import com.phonedyguard.core.v1.dto.map.MapDto;
import com.phonedyguard.core.v1.dto.map.MapSafeDto;
import com.phonedyguard.core.v1.repository.MapRepository;
import com.phonedyguard.core.v1.repository.MapSafeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class MapService {
    private final MapRepository mapRepository;
    private final MapSafeRepository mapSafeRepository;

    @Transactional
    public List<MapSafeDto> getMaplist() {
        List<MapSafeEntity> mapSafeEntities = mapSafeRepository.findAll();
        List<MapSafeDto> mapSafeDtoList = new ArrayList<>();

        for ( MapSafeEntity mapSafeEntity : mapSafeEntities) {
            MapSafeDto mapSafeDTO = MapSafeDto.builder()
                    .safe_latitude(mapSafeEntity.getSafe_latitude())
                    .safe_longitude(mapSafeEntity.getSafe_longitude())
                    .build();
            mapSafeDtoList.add(mapSafeDTO);
        }
        return mapSafeDtoList;
    }

    @Transactional
    public Long saveSaferoutes(MapSafeDto mapSafeDto) {
        return mapSafeRepository.save(mapSafeDto.toEntity()).getId();
    }


    @Transactional
    public Long savePosition(MapDto mapDto) {
        return mapRepository.save(mapDto.toEntity()).getId();
    }

}

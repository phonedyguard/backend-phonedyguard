package com.phonedyguard.core.v1.service;

import com.phonedyguard.core.entity.MapEntity;
import com.phonedyguard.core.v1.dto.Response;
import com.phonedyguard.core.v1.dto.map.MapDto;
import com.phonedyguard.core.v1.dto.map.MapSafeDto;
import com.phonedyguard.core.v1.repository.MapRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class MapService {
    private final MapRepository mapRepository;

    @Transactional
    public List<MapSafeDto> getMaplist() {
        List<MapEntity> mapEntities = mapRepository.findAll();
        List<MapSafeDto> mapSafeDtoList = new ArrayList<>();

        for ( MapEntity mapEntity : mapEntities) {
            MapSafeDto mapSafeDTO = MapSafeDto.builder()
                    .latitude(mapEntity.getLatitude())
                    .longitude(mapEntity.getLongitude())
                    .id(mapEntity.getId())
                    .build();

            mapSafeDtoList.add(mapSafeDTO);
        }
        return mapSafeDtoList;
    }

    @Transactional
    public Long saveSaferoutes(MapSafeDto mapSafeDto) {
        return mapRepository.save(mapSafeDto.toEntity()).getId();
    }


    @Transactional
    public Long savePosition(MapDto mapDto) {
        return mapRepository.save(mapDto.toEntity()).getId();
    }

}

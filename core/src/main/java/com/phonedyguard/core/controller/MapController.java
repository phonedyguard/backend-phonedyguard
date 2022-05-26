package com.phonedyguard.core.controller;

import com.phonedyguard.core.v1.dto.map.MapDto;
import com.phonedyguard.core.v1.repository.MapRepository;
import com.phonedyguard.core.v1.service.MapService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class MapController {
    private final MapService mapService;

    public MapController(MapService mapService, MapRepository mapRepository) {
        this.mapService = mapService;
        this.mapRepository = mapRepository;
    }
    
    @Autowired
    private final MapRepository mapRepository;

    @PostMapping(value = "/maps")
    public ResponseEntity<?> getMappingTest(@RequestBody MapDto mapDto) {
        mapDto = MapDto.builder()
                .latitude(mapDto.getLatitude())
                .longitude(mapDto.getLongitude())
                .build();
        log.info("test: " + mapDto);
        mapService.savePosition(mapDto);
        return ResponseEntity.ok(mapDto);
    }
}
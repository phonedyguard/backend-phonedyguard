package com.phonedyguard.core.controller;

import com.phonedyguard.core.v1.dto.map.MapDto;
import com.phonedyguard.core.v1.dto.map.MapSafeDto;
import com.phonedyguard.core.v1.repository.MapRepository;
import com.phonedyguard.core.v1.repository.MapSafeRepository;
import com.phonedyguard.core.v1.service.MapService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class MapController {
    private final MapService mapService;


    public MapController(MapService mapService, MapRepository mapRepository, MapSafeRepository mapSafeRepository) {
        this.mapService = mapService;
        this.mapRepository = mapRepository;
        this.mapSafeRepository = mapSafeRepository;
    }

    @Autowired
    private final MapRepository mapRepository;

    @Autowired
    private final MapSafeRepository mapSafeRepository;


    @PostMapping(value = "/maps/indices")
    public ResponseEntity<?> indices(@RequestBody MapDto mapDto) {
        mapDto = MapDto.builder()
                .latitude(mapDto.getLatitude())
                .longitude(mapDto.getLongitude())
                .build();
        log.info("test: " + mapDto);
        mapService.savePosition(mapDto);
        return ResponseEntity.ok(mapDto);
    }

    @PostMapping(value = "/maps/routes")
    public ResponseEntity<?> saveroutes(@RequestBody MapSafeDto mapsafeDto) {
        mapsafeDto = MapSafeDto.builder()
                .safe_latitude(mapsafeDto.getSafe_latitude())
                .safe_longitude(mapsafeDto.getSafe_longitude())
                .build();
        log.info("test: " + mapsafeDto);
        mapService.saveSaferoutes(mapsafeDto);
        return ResponseEntity.ok(mapsafeDto);
    }

    @GetMapping("/maps/routes")
    public ResponseEntity<List> routeslist(){
        List<MapSafeDto> mapSafeDtoList = mapService.getMaplist();
        return new ResponseEntity<List>(mapSafeDtoList, HttpStatus.OK);
    }

    @GetMapping("/")
    @RequestMapping(value="/https://apis.openapi.sk.com/tmap/routes/pedestrian?version=1&format=json&callback=result",method = {RequestMethod.POST})
    public void test(@RequestParam("startX")float startX){
        System.out.println("쉬발람" + startX);
    }
}
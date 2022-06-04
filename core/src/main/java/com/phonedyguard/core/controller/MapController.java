package com.phonedyguard.core.controller;

import com.phonedyguard.core.v1.dto.board.BoardPostDto;
import com.phonedyguard.core.v1.dto.map.MapDto;
import com.phonedyguard.core.v1.repository.MapRepository;
import com.phonedyguard.core.v1.repository.MapSafeRepository;
import com.phonedyguard.core.v1.service.MapService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

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
    public ResponseEntity<?> indices(HttpServletRequest request, @RequestBody MapDto mapDto){
        return mapService.saveMyPosition(request, mapDto);
    }

    @GetMapping("/maps/indices")
    public ResponseEntity<?> selectboard(HttpServletRequest request){
        return mapService.getPosition(request);
    }

//    @GetMapping("/ajax")
//    public String ajax(@RequestParam Map<String, Object> param) {
//
//        String id = (String) param.get("startName");
//        System.out.println("ajax start!");
//        System.out.println(id);
//        return "map.html";
//    }



}
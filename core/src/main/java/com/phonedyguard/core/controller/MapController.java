package com.phonedyguard.core.controller;

import com.phonedyguard.core.v1.dto.map.MapDto;
import com.phonedyguard.core.v1.dto.map.Routes;
import com.phonedyguard.core.v1.repository.MapRepository;
import com.phonedyguard.core.v1.repository.MapSafeRepository;
import com.phonedyguard.core.v1.service.MapService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MapController {
    private final MapService mapService;

    @Autowired
    private final MapRepository mapRepository;

    @Autowired
    private final MapSafeRepository mapSafeRepository;


    @PostMapping(value = "/maps/indices")
    public ResponseEntity<?> indices(HttpServletRequest request, @RequestBody MapDto mapDto) throws IOException {
        return mapService.saveMyPosition(request, mapDto);
    }

    @GetMapping("/maps/routes")
    public ResponseEntity<?> routes(HttpServletRequest request) {
        return mapService.getSafePosition(request);
    }

    @PostMapping("/maps/routes")
    public ResponseEntity<?> home(@RequestBody List<Routes> routes, HttpServletRequest request) {
        return mapService.saveSaferoutes(routes, request);
    }

    @GetMapping("/maps/indices")
    public ResponseEntity<?> checkMyPosition(HttpServletRequest request){
        return mapService.getPosition(request);
    }


}
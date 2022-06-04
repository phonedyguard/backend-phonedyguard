package com.phonedyguard.core.controller;

import com.phonedyguard.core.jwt.JwtAuthenticationFilter;
import com.phonedyguard.core.jwt.JwtTokenProvider;
import com.phonedyguard.core.v1.dto.Response;
import com.phonedyguard.core.v1.dto.board.BoardPostDto;
import com.phonedyguard.core.v1.dto.map.MapDto;
import com.phonedyguard.core.v1.dto.map.Routes;
import com.phonedyguard.core.v1.dto.request.MapRequestDto;
import com.phonedyguard.core.v1.dto.request.RouteDto;
import com.phonedyguard.core.v1.repository.MapRepository;
import com.phonedyguard.core.v1.repository.MapSafeRepository;
import com.phonedyguard.core.v1.service.MapService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MapController {
    private final MapService mapService;
    private final JwtTokenProvider jwtTokenProvider;
    private final Response response;

    @Autowired
    private final MapRepository mapRepository;

    @Autowired
    private final MapSafeRepository mapSafeRepository;


    @PostMapping(value = "/maps/indices")
    public ResponseEntity<?> indices(HttpServletRequest request, @RequestBody MapDto mapDto) {
        return mapService.saveMyPosition(request, mapDto);
    }

    @PostMapping("/maps/routes")
    public ResponseEntity<?> home(@RequestBody List<Routes> routes, HttpServletRequest request) {
        return mapService.saveSaferoutes(routes, request);
    }

    @GetMapping("/maps/indices")
    public ResponseEntity<?> selectboard(HttpServletRequest request){
        return mapService.getPosition(request);
    }


}
package com.phonedyguard.core.controller;

import com.phonedyguard.core.jwt.JwtAuthenticationFilter;
import com.phonedyguard.core.jwt.JwtTokenProvider;
import com.phonedyguard.core.v1.dto.Response;
import com.phonedyguard.core.v1.dto.map.MapDto;
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
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MapController {
    private final MapService mapService;
    private final JwtTokenProvider jwtTokenProvider;
    private final Response response;
    private final HomeController homeController;


//    public MapController(MapService mapService, MapRepository mapRepository, MapSafeRepository mapSafeRepository) {
//        this.mapService = mapService;
//        this.mapRepository = mapRepository;
//        this.mapSafeRepository = mapSafeRepository;
//    }

    @Autowired
    private final MapRepository mapRepository;

    @Autowired
    private final MapSafeRepository mapSafeRepository;


    @PostMapping(value = "/maps/indices")
    public ResponseEntity<?> indices(HttpServletRequest request, @RequestBody MapDto mapDto) {
        return mapService.saveMyPosition(request, mapDto);
    }

//    @GetMapping("/ajax")
//    public String ajax(@RequestParam Map<String, Object> param) {
//
//        String id = (String) param.get("startName");
//        System.out.println("ajax start!");
//        System.out.println(id);
//        return "map.html";
//    }

    @GetMapping("/ajax")
    public String list(Model model) {
        model.addAttribute("list");
        return "ajax.jsp";
    }

    @GetMapping("/maps/routes")
    public void home(Model model) {
//        String token = JwtAuthenticationFilter.resolveToken((HttpServletRequest) request);
//        if (!jwtTokenProvider.validateToken(token)) {
//            response.fail("token 만료", HttpStatus.BAD_REQUEST);
//        }
//        // token 값으로 정보 추출
//        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        MapRequestDto mapRequestDto = new MapRequestDto(35.142570,129.033908,35.147553,129.027765);

//        "startX":35.142570,
//                "startY":129.033908,
//                "endX":35.147553,
//                "endY":129.027765
//        homeController.hometo( model, "hi");

    }
}
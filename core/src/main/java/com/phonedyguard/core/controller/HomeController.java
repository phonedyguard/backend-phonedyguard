package com.phonedyguard.core.controller;

import com.phonedyguard.core.entity.MapSafeEntity;
import com.phonedyguard.core.jwt.JwtAuthenticationFilter;
import com.phonedyguard.core.jwt.JwtTokenProvider;
import com.phonedyguard.core.v1.dto.Response;
import com.phonedyguard.core.v1.dto.request.MapRequestDto;
import com.phonedyguard.core.v1.dto.request.RouteDto;
import com.phonedyguard.core.v1.service.MapService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final MapService mapService;
    private final JwtTokenProvider jwtTokenProvider;

//    @GetMapping("home")
//    public String home(HttpServletRequest request, @RequestBody MapRequestDto mapRequestDto, Model model) {
//        String token = JwtAuthenticationFilter.resolveToken((HttpServletRequest) request);
//        if (!jwtTokenProvider.validateToken(token))
//        {
//            return "Token fail";
//        }
//        // token 값으로 정보 추출
//        Authentication authentication = jwtTokenProvider.getAuthentication(token);
//
//        return hometo(mapRequestDto, model, authentication.getName());
//    }

    @GetMapping("home")
    public void home(Model model) {

        MapRequestDto mapRequestDto = new MapRequestDto(35.142570,129.033908,35.147553,129.027765);
//        "startX":35.142570,
//                "startY":129.033908,
//                "endX":35.147553,
//                "endY":129.027765

//        return hometo(model, "123hi");
    }

//    @GetMapping("/hometo")
//    public String hometo(MapRequestDto mapRequestDto, Model model, String email) {
//        System.out.println("test");
//        model.addAttribute("email", email);
//        model.addAttribute("startX", mapRequestDto.getStartX());
//        model.addAttribute("startY", mapRequestDto.getStartY());
//        model.addAttribute("endX", mapRequestDto.getEndX());
//        model.addAttribute("endY", mapRequestDto.getEndY());
//        return "map";
//    }

    @PostMapping("/maps/indices")
    public String hometo(@RequestBody MapRequestDto mapRequestDto, Model model, String email) {
        log.info("hometo");
        email = "jinyeong";
        MapRequestDto mapRequestDto2 = new MapRequestDto(35.142570,129.033908,35.147553,129.027765);
        model.addAttribute("email", email);
        model.addAttribute("startX", mapRequestDto.getStartX());
        model.addAttribute("startY", mapRequestDto.getStartY());
        model.addAttribute("endX", mapRequestDto.getEndX());
        model.addAttribute("endY", mapRequestDto.getEndY());
        return "map";
    }

    @PostMapping("/home/route")
    public ResponseEntity<?> homeGet(RouteDto routeDto, Model model) {
        System.out.println(routeDto.getEmail());
        System.out.println(routeDto.getLat());
        System.out.println(routeDto.getLng());

        List<MapSafeEntity> mapSafeEntities = new ArrayList<>();

        for (int i=0; i<routeDto.getLat().size(); i++)
        {
            MapSafeEntity mapSafeEntity = MapSafeEntity.builder()
                    .email(routeDto.getEmail())
                    .safe_latitude(routeDto.getLat().get(i))
                    .safe_longitude(routeDto.getLng().get(i))
                    .build();

            mapSafeEntities.add(mapSafeEntity);
        }
        return mapService.saveSaferoutes(mapSafeEntities);
    }

}

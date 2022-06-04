package com.phonedyguard.core.v1.service;

import com.phonedyguard.core.entity.MapEntity;
import com.phonedyguard.core.entity.MapSafeEntity;
import com.phonedyguard.core.jwt.JwtAuthenticationFilter;
import com.phonedyguard.core.jwt.JwtTokenProvider;
import com.phonedyguard.core.v1.dto.Response;
import com.phonedyguard.core.v1.dto.map.MapDto;
import com.phonedyguard.core.v1.dto.map.Routes;
import com.phonedyguard.core.v1.repository.MapRepository;
import com.phonedyguard.core.v1.repository.MapSafeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
@Slf4j
public class MapService {
    private final MapRepository mapRepository;
    private final MapSafeRepository mapSafeRepository;
    private final Response response;
    private final JwtTokenProvider jwtTokenProvider;


    @Transactional
    public ResponseEntity<?> saveMyPosition(HttpServletRequest request, MapDto mapDto) {

        String token = JwtAuthenticationFilter.resolveToken((HttpServletRequest) request);
        if (!jwtTokenProvider.validateToken(token))
        {
            return response.fail("accessToken 검증 실패", HttpStatus.BAD_REQUEST);
        }

        // token 값으로 정보 추출
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        String email = authentication.getName();
        Optional<MapEntity> mapEntity = mapRepository.findByEmail(email);
        MapEntity myPosition = mapEntity.get();
        String my_email = myPosition.getEmail();
        if(my_email.equals(null)){
            MapEntity map = new MapEntity();
            map.setEmail(email);
            map.setLatitude(map.getLatitude());
            map.setLongitude(map.getLongitude());
            mapRepository.save(map);
        }
        else{
            myPosition.setLatitude(mapDto.getLatitude());
            myPosition.setLongitude(mapDto.getLongitude());
        }
        return response.success("현재위치 저장 성공");
    }

    public ResponseEntity<?> saveSaferoutes(List<Routes> routes, HttpServletRequest request) {

        String token = JwtAuthenticationFilter.resolveToken((HttpServletRequest) request);
        if (!jwtTokenProvider.validateToken(token))
        {
            return response.fail("accessToken 검증 실패", HttpStatus.BAD_REQUEST);
        }
        // token 값으로 정보 추출
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        String email = authentication.getName();

        List<MapSafeEntity> mapSafeEntities = new ArrayList<>();
        for (int i=0; i<routes.size(); i++)
        {
            MapSafeEntity mapSafeEntity = MapSafeEntity.builder()
                    .email(email)
                    .safe_latitude(routes.get(i).getSafe_latitude())
                    .safe_longitude(routes.get(i).getSafe_longitude())
                    .build();

            mapSafeEntities.add(mapSafeEntity);
        }
        log.info("mapsafe list: " + mapSafeRepository.findAllByEmail(email).get());
        if(mapSafeRepository.findAllByEmail(email).get().isEmpty())
        {
            mapSafeRepository.saveAll(mapSafeEntities);
            return response.success("안심경로 저장");
        }
        else return response.fail("이미 존재합니다.");



    }

}

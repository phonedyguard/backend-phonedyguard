package com.phonedyguard.core.v1.service;

import com.phonedyguard.core.entity.BoardEntity;
import com.phonedyguard.core.entity.MapEntity;
import com.phonedyguard.core.entity.MapSafeEntity;
import com.phonedyguard.core.jwt.JwtAuthenticationFilter;
import com.phonedyguard.core.jwt.JwtTokenProvider;
import com.phonedyguard.core.v1.dto.Response;
import com.phonedyguard.core.v1.dto.board.BoardDto;
import com.phonedyguard.core.v1.dto.map.MapDto;
import com.phonedyguard.core.v1.dto.map.MapSafeDto;
import com.phonedyguard.core.v1.repository.MapRepository;
import com.phonedyguard.core.v1.repository.MapSafeRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class MapService {
    private final MapRepository mapRepository;
    private final MapSafeRepository mapSafeRepository;
    private final Response response;
    private final JwtTokenProvider jwtTokenProvider;




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
    public ResponseEntity<?> saveMyPosition(HttpServletRequest request, MapDto mapDto) {

        String token = JwtAuthenticationFilter.resolveToken((HttpServletRequest) request);
        if (!jwtTokenProvider.validateToken(token))
        {
            return response.fail("accessToken 검증 실패", HttpStatus.BAD_REQUEST);
        }
        // token 값으로 정보 추출
        Authentication authentication = jwtTokenProvider.getAuthentication(token);

        String email = authentication.getName();

        MapEntity mapEntity = new MapEntity();
        mapEntity.setEmail(mapEntity.getEmail());

//
//        BoardEntity boardEntity = new BoardEntity();
//        boardEntity.setEmail(email);
//        boardEntity.setContent(boardDto.getContent());
//        boardEntity.setTitle(boardDto.getTitle());
//        boardRepository.save(boardEntity);
        return response.success("게시글 작성 성공");
    }



    @Transactional
    public Long saveSaferoutes(MapSafeDto mapSafeDto) {
        return mapSafeRepository.save(mapSafeDto.toEntity()).getId();
    }


//    @Transactional
//    public Long savePosition(MapDto mapDto) {
//        return mapRepository.save(mapDto.toEntity()).getId();
//    }

}

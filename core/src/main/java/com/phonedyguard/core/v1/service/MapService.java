package com.phonedyguard.core.v1.service;

import com.phonedyguard.core.entity.MapEntity;
import com.phonedyguard.core.entity.MapSafeEntity;
import com.phonedyguard.core.entity.Tokens;
import com.phonedyguard.core.jwt.JwtAuthenticationFilter;
import com.phonedyguard.core.jwt.JwtTokenProvider;
import com.phonedyguard.core.v1.dto.Response;
import com.phonedyguard.core.v1.dto.map.MapDto;
import com.phonedyguard.core.v1.dto.map.MapSafeDto;
import com.phonedyguard.core.v1.dto.map.Routes;
import com.phonedyguard.core.v1.dto.response.MapResponseDto;
import com.phonedyguard.core.v1.repository.MapRepository;
import com.phonedyguard.core.v1.repository.MapSafeRepository;
import com.phonedyguard.core.v1.repository.TokenRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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
    private final FirebaseCloudMessageService firebaseCloudMessageService;
    private final TokenRepository tokenRepository;


    public ResponseEntity<?> saveMyPosition(HttpServletRequest request, MapDto mapDto) throws IOException {

        String token = JwtAuthenticationFilter.resolveToken((HttpServletRequest) request);
        if (!jwtTokenProvider.validateToken(token)) {
            return response.fail("accessToken 검증 실패", HttpStatus.BAD_REQUEST);
        }

        // token 값으로 정보 추출
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        String email = authentication.getName();
        Optional<MapEntity> mapEntity = mapRepository.findByEmail(email);
        MapEntity myPosition = mapEntity.get();
        String my_email = myPosition.getEmail();
        if (my_email.equals(null)) {
            MapEntity map = new MapEntity();
            map.setEmail(email);
            map.setLatitude(map.getLatitude());
            map.setLongitude(map.getLongitude());
            mapRepository.save(map);
        } else {
            myPosition.setLatitude(mapDto.getLatitude());
            myPosition.setLongitude(mapDto.getLongitude());
        }
        checkPosition(email);
        return response.success("현재위치 저장 성공");
    }

    public ResponseEntity<?> saveSaferoutes(List<Routes> routes, HttpServletRequest request) {

        String token = JwtAuthenticationFilter.resolveToken((HttpServletRequest) request);
        if (!jwtTokenProvider.validateToken(token)) {
            return response.fail("accessToken 검증 실패", HttpStatus.BAD_REQUEST);
        }
        // token 값으로 정보 추출
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        String email = authentication.getName();

        List<MapSafeEntity> mapSafeEntities = new ArrayList<>();
        for (int i = 0; i < routes.size(); i++) {
            MapSafeEntity mapSafeEntity = MapSafeEntity.builder()
                    .email(email)
                    .safe_latitude(routes.get(i).getSafe_latitude())
                    .safe_longitude(routes.get(i).getSafe_longitude())
                    .build();

            mapSafeEntities.add(mapSafeEntity);
        }
        log.info("mapsafe list: " + mapSafeRepository.findAllByEmail(email).get());
        if (mapSafeRepository.findAllByEmail(email).get().isEmpty()) {
            mapSafeRepository.saveAll(mapSafeEntities);
            return response.success("안심경로 저장");
        } else return response.fail("이미 존재합니다.");
    }

    public ResponseEntity<?> getPosition(HttpServletRequest request) {

        String token = JwtAuthenticationFilter.resolveToken((HttpServletRequest) request);
        if (!jwtTokenProvider.validateToken(token)) {
            return response.fail("accessToken 검증 실패", HttpStatus.BAD_REQUEST);
        }

        // token 값으로 정보 추출
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        String email = authentication.getName();
        Optional<MapEntity> mapEntity = mapRepository.findByEmail(email);

        MapResponseDto mapResponseDto = MapResponseDto.builder()
                .latitude(mapEntity.get().getLatitude())
                .longitude(mapEntity.get().getLongitude())
                .build();
        return ResponseEntity.ok(mapResponseDto);
    }

    public void checkPosition(String email) throws IOException {
        Optional<MapEntity> mapEntity = mapRepository.findByEmail(email);


        double range = 0.0002;
        MapEntity myPosition = mapEntity.get();
        double my_Latitude = myPosition.getLatitude();
        double my_Longitude = myPosition.getLongitude();


        //이건 필요없음 test용
        List<MapSafeEntity> mapSafeEntity = mapSafeRepository.findByEmail(email);
        List<MapSafeDto> mapSafeDto = new ArrayList<>();
        for (MapSafeEntity mapSafeEntities : mapSafeEntity) {
            MapSafeDto mapSafeDto2 = MapSafeDto.builder()
                    .safe_latitude(mapSafeEntities.getSafe_latitude())
                    .safe_longitude(mapSafeEntities.getSafe_longitude())
                    .build();
            mapSafeDto.add(mapSafeDto2);
        }

        //안심경로를 list형식으로 집어넣어줌
        List<Double> safeLatitudes = new ArrayList<>();
        List<Double> safeLongitudes = new ArrayList<>();
        for (MapSafeEntity mapSafeEntities : mapSafeEntity) {
            safeLatitudes.add(mapSafeEntities.getSafe_latitude());
            safeLongitudes.add(mapSafeEntities.getSafe_longitude());
        }

//        for (int i = 0; i < mapSafeEntity.size(); i++) {
//            System.out.println("latlng" + mapSafeEntity.get(i).getSafe_latitude() + ", " + mapSafeEntity.get(i).getSafe_longitude() + mapSafeEntity.size());
//        }
        //현재 위치 기준 안심경로의 근사값을 구해줌
        double latmin = Double.MAX_VALUE;
        double longmin = Double.MAX_VALUE;
        int nearData = 0;
        int flag = 0;
        boolean alarm = false;


        //근사값까지 검사를 끝냈다면 또 다른 근사값을 계산하여 배열이
        for (int i = nearData; i < mapSafeEntity.size(); i++) {
            double a = Math.abs(mapSafeEntity.get(i).getSafe_latitude() - my_Latitude);
            double b = Math.abs(mapSafeEntity.get(i).getSafe_longitude() - my_Longitude);
            if (latmin > a && longmin > b) {
                latmin = a; //0.00001
                longmin = b; //0.00002
                nearData = i;
            }
        }

        if (nearData < 5) {
            int temp = nearData;
            nearData += 5; //neardata가 1이면 6으로 만들어서 1~6까지 검사한다.
            //근사값 +-5로 비교
            for (int i = nearData - 5; i < nearData; i++) {
                //현재 위치 - 0.0002 < 안심경로 < 현재위치 + 0.0002 위경도 둘 다 맞을 경우
                //안심경로이기 때문에 괄호로 묶고 and연산으로 바꿈
                if(i == mapSafeEntity.size())
                    break;
                if ((my_Latitude - range <= mapSafeEntity.get(i).getSafe_latitude() && mapSafeEntity.get(i).getSafe_latitude() <= my_Latitude + range)
                        && (my_Longitude - range <= mapSafeEntity.get(i).getSafe_longitude() && mapSafeEntity.get(i).getSafe_longitude() <= my_Longitude + range)) {
                    System.out.println("안심경로");
                    alarm = true;
                    break;
                }
            }

        }



        else {
            //근사값 +-5로 비교
            for (int i = nearData - 5; i < nearData + 5; i++) {
                if(i == mapSafeEntity.size())
                    break;
                if ((my_Latitude - range <= mapSafeEntity.get(i).getSafe_latitude() && mapSafeEntity.get(i).getSafe_latitude() <= my_Latitude + range)
                        && (my_Longitude - range <= mapSafeEntity.get(i).getSafe_longitude() && mapSafeEntity.get(i).getSafe_longitude() <= my_Longitude + range)) {
                    System.out.println("안심경로");
                    alarm = true;
                    break;
                }
            }
        }

        if(!alarm)
        {
            System.out.println("안심경로이탈");

            Optional<List<Tokens>> tokens = tokenRepository.findAllByEmail(email);
            for(int i=0; i<tokens.get().size(); i++)
            {
                log.info(tokens.get().get(i).getToken());
                firebaseCloudMessageService.sendMessageTo(tokens.get().get(i).getToken(), "안심경로 이탈", "피보호자가 안심경로를 이탈하였습니다");
            }

        }
    }
}


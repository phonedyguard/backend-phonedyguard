package com.phonedyguard.core.v1.service;

import com.phonedyguard.core.entity.MapEntity;
import com.phonedyguard.core.entity.MapSafeEntity;
import com.phonedyguard.core.jwt.JwtAuthenticationFilter;
import com.phonedyguard.core.jwt.JwtTokenProvider;
import com.phonedyguard.core.v1.dto.Response;
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
import java.util.Optional;

@AllArgsConstructor
@Service
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
        checkPosition(email);
        return response.success("현재위치 저장 성공");
    }


    @Transactional
    public ResponseEntity<?> getPosition(HttpServletRequest request){

        String token = JwtAuthenticationFilter.resolveToken((HttpServletRequest) request);
        if (!jwtTokenProvider.validateToken(token))
        {
            return response.fail("accessToken 검증 실패", HttpStatus.BAD_REQUEST);
        }

        // token 값으로 정보 추출
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        String email = authentication.getName();
        Optional<MapEntity> mapEntity = mapRepository.findByEmail(email);

        MapEntity getPosition = mapEntity.get();
        getPosition.getLatitude();
        getPosition.getLongitude();
        return response.success("현재위치 보내기");
    }

    @Transactional
    public void checkPosition(String email) {
//        String token = JwtAuthenticationFilter.resolveToken((HttpServletRequest) request);
//
//        // token 값으로 정보 추출
//        Authentication authentication = jwtTokenProvider.getAuthentication(token);
//        String email = authentication.getName();
        Optional<MapEntity> mapEntity = mapRepository.findByEmail(email);


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

        //현재 위치 기준 안심경로의 근사값을 구해줌
        double latmin = Double.MAX_VALUE;
        double longmin = Double.MAX_VALUE;
        int nearData = 0;


        while (true) {
            //안심경로 안내 종료
            if (nearData == safeLatitudes.size())
                break;
            //근사값까지 검사를 끝냈다면 또 다른 근사값을 계산하여 배열이
            for (int i = nearData; i < safeLatitudes.size(); i++) {
                double a = Math.abs(safeLatitudes.get(i) - my_Latitude);
                double b = Math.abs(safeLongitudes.get(i) - my_Longitude);
                if (latmin > a || longmin > b) {
                    latmin = a; //0.00001
                    longmin = b; //0.00002
                    nearData = i;
                } else {
                    break;
                }
            }

            if (nearData < 5) {
                int temp = nearData;
                nearData += 5; //neardata가 1이면 6으로 만들어서 1~6까지 검사한다.
                //근사값 +-5로 비교
                for (int i = nearData - 5; i < nearData; i++) {
                    //현재 위치 - 0.0002 < 안심경로 < 현재위치 + 0.0002 위경도 둘 다 맞을 경우
                    //안심경로이기 때문에 괄호로 묶고 and연산으로 바꿈
                    if ((my_Latitude - 0.0002 <= safeLatitudes.get(i) && safeLatitudes.get(i) <= my_Latitude + 0.0002)
                            && (my_Longitude - 0.0002 <= safeLongitudes.get(i) && safeLongitudes.get(i) <= my_Longitude + 0.0002)) {
                        System.out.println("안심경로");
                    } else {
                        System.out.println("안심경로 이탈 알림!");
                        break;
                    }
                }
                nearData = temp; //neardata값을 임의로 바꿨기 때문에 다시 원래 값으로
            } else {
                //근사값 +-5로 비교
                for (int i = nearData - 5; i < nearData + 5; i++) {
                    if ((my_Latitude - 0.0002 <= safeLatitudes.get(i) && safeLatitudes.get(i) <= my_Latitude + 0.0002)
                            && (my_Longitude - 0.0002 <= safeLongitudes.get(i) && safeLongitudes.get(i) <= my_Longitude + 0.0002)) {
                        System.out.println("안심경로");
                    } else {
                        System.out.println("안심경로 이탈 알림!");
                        break;
                    }
                }
            }
        }
    }

//
//        //안심경로를 list형식으로 집어넣어줌
//        List<Double> safeLatitudes = new ArrayList<>();
//        List<Double> safeLongitudes = new ArrayList<>();
//        for ( MapSafeEntity mapSafeEntities : mapSafeEntity) {
//            safeLatitudes.add(mapSafeEntities.getSafe_latitude());
//            safeLongitudes.add(mapSafeEntities.getSafe_longitude());
//        }
//
//        //현재 위치 기준 안심경로의 근사값을 구해줌
//        double latmin = Double.MAX_VALUE;
//        double longmin = Double.MAX_VALUE;
//        int nearData = 0;
//        for(int i = 0 ; i < safeLatitudes.size(); i++){
//            double a = Math.abs(safeLatitudes.get(i) - my_Latitude);
//            double b = Math.abs(safeLongitudes.get(i) - my_Longitude);
//            if (latmin > a || longmin > b){
//                latmin = a;
//                longmin = b;
//                nearData = i;
//                //이건 밑에 for문 돌릴때 i가 0이되면 안되서 ...
//                if (nearData < 4){
//                    nearData=5;
//                }
//            }
//            else{
//                break;
//            }
//        }
//
//        //근사값 +-5로 비교
//        for (int i = nearData-5; i < nearData+5; i++){
//            if(my_Latitude-0.0002 <= safeLatitudes.get(i)
//                    && safeLatitudes.get(i) <= my_Latitude+0.0002
//                    || my_Longitude-0.0002 <= safeLongitudes.get(i)
//                    && safeLongitudes.get(i) <= my_Longitude+0.0002){
//                System.out.println("시발!! ");
//            }
//        }
//
//    }

}


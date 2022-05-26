package com.phonedyguard.core.v1.service;

import com.phonedyguard.core.entity.MapEntity;
import com.phonedyguard.core.v1.dto.Response;
import com.phonedyguard.core.v1.dto.map.MapDto;
import com.phonedyguard.core.v1.repository.MapRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class MapService {
    private final MapRepository mapRepository;
    Response response;

    @Transactional
    public Long savePosition(MapDto mapDto) {
        return mapRepository.save(mapDto.toEntity()).getId();
    }
}

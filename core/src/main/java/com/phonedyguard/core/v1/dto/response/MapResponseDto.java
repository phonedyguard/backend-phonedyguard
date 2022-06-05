package com.phonedyguard.core.v1.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


public class MapResponseDto {
    @Getter
    @Setter
    @Builder
    public static class Indices{
        private double latitude;
        private double longitude;
    }

    @Getter
    @Setter
    @Builder
    public static class SafeIndices{
        private double start_lat;
        private double start_lon;
        private double end_lat;
        private double end_lon;
    }

}

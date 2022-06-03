package com.phonedyguard.core.v1.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RouteDto {
    private String email;
    private List<Double> lat;
    private List<Double> lng;
}

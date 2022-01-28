package com.epam.cargo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryCostCalculatorRequest {
    private Long cityFromId;
    private Long cityToId;

    private DimensionsRequest dimensions;
    private Double weight;

    public static DeliveryCostCalculatorRequest of(Long cityFromId, Long cityToId, DimensionsRequest dimensions, Double weight){
        DeliveryCostCalculatorRequest request = new DeliveryCostCalculatorRequest();
        request.setCityFromId(cityFromId);
        request.setCityToId(cityToId);
        request.setDimensions(dimensions);
        request.setWeight(weight);
        return request;
    }
}
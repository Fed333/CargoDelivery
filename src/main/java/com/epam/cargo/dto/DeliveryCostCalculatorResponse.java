package com.epam.cargo.dto;

import com.epam.cargo.entity.City;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryCostCalculatorResponse {
    private City.Distance distance;
    private Double cost;

    public DeliveryCostCalculatorResponse() { }

    public DeliveryCostCalculatorResponse(Double cost, City.Distance distance) {
        this.distance = distance;
        this.cost = cost;
    }

    public static DeliveryCostCalculatorResponse of(City.Distance distance, Double cost){
        DeliveryCostCalculatorResponse response = new DeliveryCostCalculatorResponse();
        response.setDistance(distance);
        response.setCost(cost);
        return response;
    }
}

package com.epam.cargo.dto;

import com.epam.cargo.entity.BaggageType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveredBaggageRequest {

    private Double weight;

    private Double volume;

    private BaggageType type;

    private String description;

}

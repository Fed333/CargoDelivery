package com.epam.cargo.dto;

import com.epam.cargo.entity.BaggageType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import static com.epam.cargo.exception.WrongInput.*;

/**
 * Data Transfer Object to assemble baggage on making delivery application page.<br>
 * @author Roman Kovalchuk
 * @version 1.0
 * */
@Getter
@Setter
@Builder
public class DeliveredBaggageRequest {

    @NotNull(message = REQUIRED)
    @Positive(message = NO_POSITIVE_NUMBER)
    private Double weight;

    @NotNull(message = REQUIRED)
    @Positive(message = NO_POSITIVE_NUMBER)
    private Double volume;

    @NotNull(message = REQUIRED)
    private BaggageType type;

    private String description;

    public DeliveredBaggageRequest() {
    }

    public DeliveredBaggageRequest(Double weight, Double volume, BaggageType type, String description) {
        this.weight = weight;
        this.volume = volume;
        this.type = type;
        this.description = description;
    }
}
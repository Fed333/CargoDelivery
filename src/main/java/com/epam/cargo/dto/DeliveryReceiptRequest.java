package com.epam.cargo.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import static com.epam.cargo.exception.WrongInput.NO_POSITIVE_NUMBER;
import static com.epam.cargo.exception.WrongInput.REQUIRED;

@Getter
@Setter
public class DeliveryReceiptRequest {

    @NotNull(message = REQUIRED)
    @Positive(message = NO_POSITIVE_NUMBER)
    private Double price;

}

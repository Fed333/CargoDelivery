package com.epam.cargo.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static com.epam.cargo.exception.WrongInput.*;

/**
 * Data Transfer Object to assemble address.<br>
 * @author Roman Kovalchuk
 * @version 1.0
 * */
@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class AddressRequest {

    @NotNull(message = REQUIRED)
    private Long cityId;

    @NotNull(message = REQUIRED)
    @NotBlank(message = REQUIRED)
    private String streetName;

    @NotNull(message = REQUIRED)
    @NotBlank(message = REQUIRED)
    private String houseNumber;
}
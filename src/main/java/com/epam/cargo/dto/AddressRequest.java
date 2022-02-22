package com.epam.cargo.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static com.epam.cargo.exception.WrongInput.*;

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
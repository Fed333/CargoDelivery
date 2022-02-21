package com.epam.cargo.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static com.epam.cargo.exception.WrongInputDataKeysConstants.REQUIRED_KEY_ERROR_MESSAGE;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class AddressRequest {

    @NotNull(message = REQUIRED_KEY_ERROR_MESSAGE)
    private Long cityId;

    @NotNull(message = REQUIRED_KEY_ERROR_MESSAGE)
    @NotBlank(message = REQUIRED_KEY_ERROR_MESSAGE)
    private String streetName;

    @NotNull(message = REQUIRED_KEY_ERROR_MESSAGE)
    @NotBlank(message = REQUIRED_KEY_ERROR_MESSAGE)
    private String houseNumber;
}

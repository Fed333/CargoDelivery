package com.epam.cargo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressRequest {
    private Long cityId;
    private String streetName;
    private String houseNumber;
}

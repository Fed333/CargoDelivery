package com.epam.cargo.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object to assemble data for DirectionDelivery objects on directions page.<br>
 * @author Roman Kovalchuk
 * @version 1.0
 * */
@Getter
@Setter
public class DirectionDeliveryFilterRequest {
    private String senderCityName;
    private String receiverCityName;

}

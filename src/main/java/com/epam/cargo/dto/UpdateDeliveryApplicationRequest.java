package com.epam.cargo.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

import static com.epam.cargo.exception.WrongInput.REQUIRED;

/**
 * Data Transfer Object to assemble updated data of DeliveryApplication.<br>
 * @author Roman Kovalchuk
 * @version 1.0
 * */
@Getter
@Setter
public class UpdateDeliveryApplicationRequest {

    @Valid
    @NotNull(message = REQUIRED)
    private AddressRequest senderAddress;

    @Valid
    @NotNull(message = REQUIRED)
    private AddressRequest receiverAddress;

    @Valid
    @NotNull(message = REQUIRED)
    private DeliveredBaggageRequest deliveredBaggageRequest;

    @NotNull(message = REQUIRED)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private LocalDate sendingDate;

    @NotNull(message = REQUIRED)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private LocalDate receivingDate;

}

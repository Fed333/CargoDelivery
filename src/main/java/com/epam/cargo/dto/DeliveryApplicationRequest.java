package com.epam.cargo.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.time.LocalDate;

@Getter
@Setter
public class DeliveryApplicationRequest {

    private AddressRequest senderAddress;
    private AddressRequest receiverAddress;

    private DeliveredBaggageRequest deliveredBaggageRequest;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private LocalDate sendingDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private LocalDate receivingDate;

}

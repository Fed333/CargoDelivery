package com.epam.cargo.dto;

import com.epam.cargo.entity.DeliveryApplication;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

import static com.epam.cargo.exception.WrongInputDataKeysConstants.NO_POSITIVE_NUMBER_KEY_ERROR_MESSAGE;
import static com.epam.cargo.exception.WrongInputDataKeysConstants.REQUIRED_KEY_ERROR_MESSAGE;

@Getter
@Setter
public class UpdateDeliveryApplicationRequest {

    @Valid
    @NotNull(message = REQUIRED_KEY_ERROR_MESSAGE)
    private AddressRequest senderAddress;

    @Valid
    @NotNull(message = REQUIRED_KEY_ERROR_MESSAGE)
    private AddressRequest receiverAddress;

    @Valid
    @NotNull(message = REQUIRED_KEY_ERROR_MESSAGE)
    private DeliveredBaggageRequest deliveredBaggageRequest;

    @NotNull(message = REQUIRED_KEY_ERROR_MESSAGE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private LocalDate sendingDate;

    @NotNull(message = REQUIRED_KEY_ERROR_MESSAGE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private LocalDate receivingDate;

    @NotNull(message = REQUIRED_KEY_ERROR_MESSAGE)
    private DeliveryApplication.State state;

    @NotNull(message = REQUIRED_KEY_ERROR_MESSAGE)
    @Positive(message = NO_POSITIVE_NUMBER_KEY_ERROR_MESSAGE)
    private Double price;

}

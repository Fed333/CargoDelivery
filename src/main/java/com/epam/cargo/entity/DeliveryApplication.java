package com.epam.cargo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "delivery_applications")
@Getter
@Setter
public class DeliveryApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="sender_address_id")
    private Address senderAddress;

    @OneToOne
    @JoinColumn(name="receiver_address_id")
    private Address receiverAddress;

    @OneToOne
    @JoinColumn(name="baggageId")
    private DeliveredBaggage deliveredBaggage;

    @Column(name = "sending_date")
    private LocalDate sendingDate;

    @Column(name = "receiving_date")
    private LocalDate receivingDate;

}

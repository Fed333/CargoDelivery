package com.epam.cargo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="directions")
@Getter
@Setter
public class DirectionDelivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="sender_city_id")
    private City senderCity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="receiver_city_id")
    private City receiverCity;

    @Column(name="distance")
    private Double distance;

}

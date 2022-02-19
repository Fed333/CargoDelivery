package com.epam.cargo.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "delivered_baggage")
@Getter
@Setter
@Builder
public class DeliveredBaggage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "volume")
    private Double volume;

    @Enumerated(EnumType.ORDINAL)
    private BaggageType type;

    @Column(name = "description")
    private String description;

    public DeliveredBaggage() {
    }

    public DeliveredBaggage(Long id, Double weight, Double volume, BaggageType type, String description) {
        this.id = id;
        this.weight = weight;
        this.volume = volume;
        this.type = type;
        this.description = description;
    }
}

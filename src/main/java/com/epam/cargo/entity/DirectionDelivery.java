package com.epam.cargo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

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

    public DirectionDelivery() {
    }

    public DirectionDelivery(City senderCity, City receiverCity, Double distance) {
        this.senderCity = senderCity;
        this.receiverCity = receiverCity;
        this.distance = distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DirectionDelivery)) return false;
        DirectionDelivery that = (DirectionDelivery) o;
        return id.equals(that.id) &&
                senderCity.equals(that.senderCity) &&
                receiverCity.equals(that.receiverCity) &&
                distance.equals(that.distance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, senderCity, receiverCity, distance);
    }

    @Override
    public String toString() {
        return "DirectionDelivery{" +
                "id=" + id +
                ", senderCity=" + senderCity +
                ", receiverCity=" + receiverCity +
                ", distance=" + distance +
                '}';
    }
}

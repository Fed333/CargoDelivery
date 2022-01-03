package com.epam.cargo.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.repository.cdi.Eager;

import javax.persistence.*;

@Entity
@Table(name="addresses")
@Getter
@Setter
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="city_id")
    private City city;

    @Column(name = "street")
    private String street;

    @Column(name="house_number")
    private String houseNumber;
}

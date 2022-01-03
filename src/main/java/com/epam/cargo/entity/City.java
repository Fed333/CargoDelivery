package com.epam.cargo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="cities")
@Getter
@Setter
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="zipcode", unique = true)
    private String zipcode;

    public City() {
    }

    public City(String name, String zipcode) {
        this.name = name;
        this.zipcode = zipcode;
    }

}

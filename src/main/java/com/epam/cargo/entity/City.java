package com.epam.cargo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof City)) return false;
        City city = (City) o;
        return id.equals(city.id) &&
                name.equals(city.name) &&
                zipcode.equals(city.zipcode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, zipcode);
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", zipcode='" + zipcode + '\'' +
                '}';
    }
}

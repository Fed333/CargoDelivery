package com.epam.cargo.repos;

import com.epam.cargo.entity.City;
import com.epam.cargo.entity.DirectionDelivery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DirectionDeliveryRepo extends JpaRepository<DirectionDelivery, Long> {
    @Override
    Optional<DirectionDelivery> findById(Long id);   //untested

    @Override
    List<DirectionDelivery> findAll();               //untested

}

package com.epam.cargo.repos;

import com.epam.cargo.entity.DimensionsFare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Repository of fetching DimensionsFare objects from database.
 * @author Roman Kovalchuk
 * @version 1.0
 * */
public interface DimensionsFareRepo extends JpaRepository<DimensionsFare, Long> {

    @Query(value = "SELECT f FROM DimensionsFare f WHERE ?1 BETWEEN f.dimensionsFrom AND f.dimensionsTo")
    DimensionsFare findFareByVolume(Integer volume);

    @Query(value = " SELECT * FROM dimensions_fares WHERE price = (SELECT MAX(price) FROM dimensions_fares)", nativeQuery = true)
    DimensionsFare findMaxFare();

}

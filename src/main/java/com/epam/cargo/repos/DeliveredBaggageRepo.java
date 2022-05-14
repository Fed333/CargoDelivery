package com.epam.cargo.repos;

import com.epam.cargo.custom.AmountBaggagePerType;
import com.epam.cargo.entity.DeliveredBaggage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Repository of fetching DeliveredBaggage objects from database.
 * @author Roman Kovalchuk
 * @version 1.0
 * */
public interface DeliveredBaggageRepo extends JpaRepository<DeliveredBaggage, Long> {

    @Query("SELECT " +
            "new com.epam.cargo.custom.AmountBaggagePerType(b.type, COUNT(b.type))" +
            "FROM DeliveredBaggage b " +
            "GROUP BY b.type")
    List<AmountBaggagePerType> countBaggagePerType();
}
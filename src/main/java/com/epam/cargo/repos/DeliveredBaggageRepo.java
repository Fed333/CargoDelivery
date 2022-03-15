package com.epam.cargo.repos;

import com.epam.cargo.entity.DeliveredBaggage;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository of fetching DeliveredBaggage objects from database.
 * @author Roman Kovalchuk
 * @version 1.0
 * */
public interface DeliveredBaggageRepo extends JpaRepository<DeliveredBaggage, Long> {

}

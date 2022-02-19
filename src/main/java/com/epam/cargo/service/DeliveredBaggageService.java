package com.epam.cargo.service;

import com.epam.cargo.dto.DeliveredBaggageRequest;
import com.epam.cargo.entity.DeliveredBaggage;
import com.epam.cargo.repos.DeliveredBaggageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class DeliveredBaggageService {

    @Autowired
    private DeliveredBaggageRepo deliveredBaggageRepo;

    public Optional<DeliveredBaggage> findById(Long id){
        return deliveredBaggageRepo.findById(id);
    }

    /**
     * Save delivered baggage in the database
     * @param deliveredBaggage baggage to add
     * @throws IllegalArgumentException whereas any of mandatory attributes are missing
     * @return true whether deliveredBaggage was saved successful, false if param is null
     * */
    public boolean save(DeliveredBaggage deliveredBaggage) {
        if (Objects.isNull(deliveredBaggage)){
            return false;
        }
        requireValidBaggage(deliveredBaggage);
        deliveredBaggageRepo.save(deliveredBaggage);
        return true;
    }

    private void requireValidBaggage(DeliveredBaggage deliveredBaggage) {
        Optional.ofNullable(deliveredBaggage.getWeight()).orElseThrow(IllegalArgumentException::new);
        Optional.ofNullable(deliveredBaggage.getVolume()).orElseThrow(IllegalArgumentException::new);
        Optional.ofNullable(deliveredBaggage.getType()).orElseThrow(IllegalArgumentException::new);
    }

    /**
     * Updates state of already existing baggage
     * @param deliveredBaggage updating baggage object
     * @param deliveredBaggageRequest updating data
     * @return updated DeliveredBaggage object
     * */
    public DeliveredBaggage update(DeliveredBaggage deliveredBaggage, DeliveredBaggageRequest deliveredBaggageRequest) {
        Objects.requireNonNull(deliveredBaggage, "Delivered Baggage from db cannot be null");
        Objects.requireNonNull(deliveredBaggageRequest, "Delivered Baggage Request cannot be null");
        if (deliveredBaggageRepo.findById(deliveredBaggage.getId()).isEmpty()){
            throw new IllegalArgumentException("Param deliveredBaggage doesn't exist in db");
        }
        DeliveredBaggage updated = ServiceUtils.createDeliveredBaggage(deliveredBaggageRequest);
        updated.setId(deliveredBaggage.getId());
        return deliveredBaggageRepo.save(updated);
    }
}

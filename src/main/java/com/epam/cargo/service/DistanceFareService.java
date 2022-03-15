package com.epam.cargo.service;

import com.epam.cargo.entity.DistanceFare;
import com.epam.cargo.repos.DistanceFareRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * Service class for obtaining DistanceFare and calculating its price.<br>
 * @author Roman Kovalchuk
 * @see DistanceFare
 * @version 1.0
 * */
@Service
public class DistanceFareService {

    @Autowired
    private DistanceFareRepo distanceFareRepo;

    public List<DistanceFare> findAllFares(){
        return distanceFareRepo.findAll();
    }

    public DistanceFare findFareByDistance(Integer distance){
        requireOnlyPositive(distance);

        DistanceFare fare = distanceFareRepo.findFareByDistance(distance);
        if (Objects.isNull(fare)){
            fare = distanceFareRepo.findMaxFare();
        }
        return fare;
    }

    /**
     * @param distance of transported baggage in km
     * @return price according to found fare, if value is out of any fares bound, will be returned a price of max fare
     * */
    public Double getPrice(Integer distance){
        return findFareByDistance(distance).getPrice();
    }

    private void requireOnlyPositive(Integer distance) {
        if (distance < 0){
            throw new IllegalArgumentException("distance cannot be negative");
        }
    }
}

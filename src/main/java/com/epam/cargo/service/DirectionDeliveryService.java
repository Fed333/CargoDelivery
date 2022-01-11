package com.epam.cargo.service;

import com.epam.cargo.entity.City;
import com.epam.cargo.entity.DirectionDelivery;
import com.epam.cargo.repos.DirectionDeliveryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DirectionDeliveryService {
    @Autowired
    private DirectionDeliveryRepo directionDeliveryRepo;

    public boolean addDirection(City sender, City receiver, Double distance){
        DirectionDelivery direction = new DirectionDelivery(sender, receiver, distance);
        return addDirection(direction);
    }

    public boolean addDirection(DirectionDelivery direction){
        requireNonNullAndSimilarCities(direction);

        DirectionDelivery foundDelivery;
        if (Objects.isNull(
                foundDelivery = directionDeliveryRepo.findBySenderCityAndReceiverCity(
                        direction.getSenderCity(),
                        direction.getReceiverCity()
                ))
        ){
            directionDeliveryRepo.save(direction);
            return true;
        }
        direction.setId(foundDelivery.getId());
        return false;
    }

    public DirectionDelivery findById(Long id) {
        return directionDeliveryRepo.findById(id).orElseThrow();
    }


    public DirectionDelivery findBySenderCityAndReceiverCity(City senderCity, City receiverCity){
        return directionDeliveryRepo.findBySenderCityAndReceiverCity(senderCity, receiverCity);
    }

    public List<DirectionDelivery> findAll() {
        return directionDeliveryRepo.findAll();
    }
    public Page<DirectionDelivery> findAll(Pageable pageable) {
        return directionDeliveryRepo.findAll(pageable);
    }

    public void deleteDirection(DirectionDelivery direction) {
        directionDeliveryRepo.delete(direction);
    }

    private void requireNonNullAndSimilarCities(DirectionDelivery direction){
        City city1 = Optional.ofNullable(direction.getSenderCity()).orElseThrow();
        City city2 = Optional.ofNullable(direction.getReceiverCity()).orElseThrow();
        if (city1.equals(city2)){
            throw new IllegalArgumentException();
        }
    }
}

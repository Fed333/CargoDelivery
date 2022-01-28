package com.epam.cargo.service;

import com.epam.cargo.dto.DeliveryCostCalculatorRequest;
import com.epam.cargo.dto.DeliveryCostCalculatorResponse;
import com.epam.cargo.entity.City;
import com.epam.cargo.exception.NoExistingCityException;
import com.epam.cargo.exception.WrongDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Objects;

@Service
public class DeliveryCostCalculatorService {
    @Autowired
    private DistanceFareService distanceFareService;

    @Autowired
    private WeightFareService weightFareService;

    @Autowired
    private DimensionsFareService dimensionsFareService;

    @Autowired
    private DirectionDeliveryService directionDeliveryService;

    @Autowired
    private CityService cityService;

    public DeliveryCostCalculatorResponse calculateCost(DeliveryCostCalculatorRequest calculatorRequest, Locale locale) throws WrongDataException {
        Objects.requireNonNull(calculatorRequest, "calculatorRequest cannot be null");

        Long cityFromId = calculatorRequest.getCityFromId();
        Long cityToId = calculatorRequest.getCityToId();
        requireDifferentCities(cityFromId, cityToId);

        City cityFrom = requireExistingCity(cityFromId);
        City cityTo = requireExistingCity(cityToId);

        City.Distance distance = directionDeliveryService.getDistanceBetweenCities(cityFrom, cityTo, locale);

        double distanceCost = distanceFareService.getPrice(distance.getDistance().intValue());
        double weightCost = weightFareService.getPrice(calculatorRequest.getWeight().intValue());
        double dimensionsCost = dimensionsFareService.getPrice(calculatorRequest.getDimensions().getVolume().intValue());

        double totalCost = distanceCost + weightCost + dimensionsCost;

        return new DeliveryCostCalculatorResponse(totalCost, distance);
    }

    private void requireDifferentCities(Long cityFromId, Long cityToId) {
        if (Objects.equals(cityFromId, cityToId)){
            throw new IllegalArgumentException("Sender city and receiver city cannot be the same");
        }
    }

    private City requireExistingCity(Long cityId) throws NoExistingCityException {
        City city = cityService.findCityById(cityId);
        if (Objects.isNull(city)){
            throw new NoExistingCityException();
        }
        return city;
    }

}

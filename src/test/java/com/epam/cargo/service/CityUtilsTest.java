package com.epam.cargo.service;

import com.epam.cargo.entity.City;
import com.epam.cargo.entity.DirectionDelivery;
import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest
class CityUtilsTest {
    private static final String kyivZipCode = "01001-06999";
    private static final String cherkasyZipCode = "18000";
    private static final String umanZipCode = "20301";
    private static final String vinnytisaZipCode = "21012";
    private static final String zhytomyrZipCode = "10000-10031";


    @Autowired
    private DirectionDeliveryService directionDeliveryService;
    
    @Autowired
    private CityService cityService;

    private static Stream<Arguments> testDistanceCalculationCases() {
        return Stream.of(
                Arguments.of(kyivZipCode, cherkasyZipCode,  192.3),
                Arguments.of(kyivZipCode, umanZipCode,  375.9),
                Arguments.of(kyivZipCode, vinnytisaZipCode,  268.6),
                Arguments.of(kyivZipCode, zhytomyrZipCode,  139.9)
        );
    }

    private static Stream<Arguments> testSmallestRouteBuildingCases() {
        return Stream.of(
                Arguments.of(kyivZipCode, cherkasyZipCode, Arrays.asList(kyivZipCode, cherkasyZipCode)),
                Arguments.of(kyivZipCode, umanZipCode,  Arrays.asList(kyivZipCode, cherkasyZipCode, umanZipCode)),
                Arguments.of(kyivZipCode, vinnytisaZipCode, Arrays.asList(kyivZipCode, zhytomyrZipCode, vinnytisaZipCode)),
                Arguments.of(kyivZipCode, zhytomyrZipCode,  Arrays.asList(kyivZipCode, zhytomyrZipCode))
        );
    }


    @ParameterizedTest
    @MethodSource(value = "testDistanceCalculationCases")
    public void testDistanceCalculation(String zipcode1, String zipcode2, Double expected){
        City city1 = cityService.findCityByZipCode(zipcode1);
        City city2 = cityService.findCityByZipCode(zipcode2);
        List<DirectionDelivery> directions = directionDeliveryService.findAll(Locale.UK);
        Double weight = CityUtils.getDistance(city1, city2, directions).getDistance();
        Assert.assertEquals(expected, weight);
    }

    @ParameterizedTest
    @MethodSource(value = "testSmallestRouteBuildingCases")
    public void testSmallestRoutBuilding(String zipcode1, String zipcode2, List<String> expectedZipcodes){
        City city1 = cityService.findCityByZipCode(zipcode1);
        City city2 = cityService.findCityByZipCode(zipcode2);
        List<DirectionDelivery> directions = directionDeliveryService.findAll(Locale.UK);
        List<City> route = CityUtils.getDistance(city1, city2, directions).getRoute();
        List<String> zipcodes = route.stream().map(City::getZipcode).collect(Collectors.toList());
        Assert.assertEquals(expectedZipcodes, zipcodes);
    }
}
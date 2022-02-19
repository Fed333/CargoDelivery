package com.epam.cargo.util;

import com.epam.cargo.entity.DimensionsFare;
import com.epam.cargo.entity.DistanceFare;
import com.epam.cargo.entity.WeightFare;
import com.epam.cargo.repos.DimensionsFareRepo;
import com.epam.cargo.repos.DistanceFareRepo;
import com.epam.cargo.repos.WeightFareRepo;
import org.mockito.Mockito;

public class MockEnvironment {

    public static void mockDimensionsFareRepo(DimensionsFareRepo dimensionsFareRepo, Integer volume, FareDataMock<DimensionsFare> dataMock) {
        Mockito.doReturn(dataMock.fareValueBetweenBy(volume, DimensionsFare::getDimensionsFrom, DimensionsFare::getDimensionsTo))
                .when(dimensionsFareRepo)
                .findFareByVolume(volume);

        Mockito.doReturn(dataMock.maxFare(DimensionsFare::getPrice))
                .when(dimensionsFareRepo)
                .findMaxFare();
    }


    public static void mockDistanceFareRepo(DistanceFareRepo fareRepo, Integer distance, FareDataMock<DistanceFare> dataMock) {
        Mockito.doReturn(dataMock.fareValueBetweenBy(distance, DistanceFare::getDistanceFrom, DistanceFare::getDistanceTo))
                .when(fareRepo)
                .findFareByDistance(distance);

        Mockito.doReturn(dataMock.maxFare(DistanceFare::getPrice))
                .when(fareRepo)
                .findMaxFare();
    }

    public static void mockWeightFareRepo(WeightFareRepo fareRepo, Double weight, FareDataMock<WeightFare> dataMock) {
        Mockito.doReturn(dataMock.fareValueBetweenBy(weight.intValue(), WeightFare::getWeightFrom, WeightFare::getWeightTo))
                .when(fareRepo)
                .findFareByWeight(weight.intValue());

        Mockito.doReturn(dataMock.maxFare(WeightFare::getPrice))
                .when(fareRepo)
                .findMaxFare();
    }

}

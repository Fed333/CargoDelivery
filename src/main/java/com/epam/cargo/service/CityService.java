package com.epam.cargo.service;

import com.epam.cargo.entity.City;
import com.epam.cargo.repos.CityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityService {
    @Autowired
    private CityRepo cityRepo;

    public boolean addCity(City city){
        if (cityRepo.findByZipcode(city.getZipcode()).isPresent()){
            return false;
        }
        cityRepo.save(city);

        return true;
    }

    public City findCityById(Long id){
        return cityRepo.findById(id).orElseThrow();
    }

    public City findCityByZipCode(String zipcode) {
        return cityRepo.findByZipcode(zipcode).orElseThrow();
    }
}

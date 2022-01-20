package com.epam.cargo.service;

import com.epam.cargo.entity.City;
import com.epam.cargo.repos.CityRepo;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.ResourceBundle;

@Service
public class CityService {

    @Value("${spring.messages.basename}")
    private String messages;

    @Autowired
    private CityRepo cityRepo;

    /**
     * add City object to database if city with such zipcode is absent
     * @param city city for adding
     * @return true if object was successfully added, otherwise false
     * */
    public boolean addCity(City city){
        if (cityRepo.findByZipcode(city.getZipcode()).isPresent()){
            return false;
        }
        cityRepo.save(city);

        return true;
    }
    /**
     * find city in database according to the given id
     * @param id city identifier in database
     * @return found City object or null if absent
     * */
    public City findCityById(Long id){
        return cityRepo.findById(id).orElse(null);
    }

    /**
     * find city in database according given zipcode
     * @param zipcode post code of City object
     * @return found City object or null if city with given zipcode is absent
     * */
    public City findCityByZipCode(String zipcode) {
        return cityRepo.findByZipcode(zipcode).orElse(null);
    }

    /**
     * localize given city according to given bundle
     * @param city object to localize
     * @param locale locale for localization
     * @return new object localized by needed locale
     * */
    @SneakyThrows
    public City localize(City city, Locale locale){
       return localize(city, ResourceBundle.getBundle(messages, locale));
    }

    /**
     * localize given city according to given bundle
     * @param city object to localize
     * @param bundle mean for localization
     * @return new object localized by needed locale
     * */
    @SneakyThrows
    public City localize(City city, ResourceBundle bundle){
        City localizedCity = (City) city.clone();
        localizedCity.setName(bundle.getString("city."+city.getName()));
        return localizedCity;
    }
}

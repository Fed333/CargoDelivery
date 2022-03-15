package com.epam.cargo.exception;

import com.epam.cargo.entity.City;

import java.util.ResourceBundle;

import static com.epam.cargo.exception.ModelErrorAttribute.ABSENT_CITY;
import static com.epam.cargo.exception.WrongInput.NO_EXISTING_CITY;

/**
 * Exception class in a case of missing City object.
 * @see City
 * @author Roman Kovalchuk
 * @version  1.0
 * */
public class NoExistingCityException extends WrongDataException{

    /**
     * Constructor for default error message.
     * @since 1.0
     * */
    public NoExistingCityException(){
        super("Your city is absent in our data base!");
    }

    /**
     * Constructor for localized error message with rejected City.
     * @since 1.0
     **/
    public NoExistingCityException(City city, ResourceBundle bundle) {
        super(buildErrorMessage(city, bundle));
    }

    private static String buildErrorMessage(City city, ResourceBundle bundle) {
        return String.format(bundle.getString(NO_EXISTING_CITY), city.getName());
    }

    @Override
    public String getModelAttribute() {
        return ABSENT_CITY.getAttr();
    }
}

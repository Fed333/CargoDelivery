package com.epam.cargo.exception;

import com.epam.cargo.entity.City;

import java.util.ResourceBundle;

import static com.epam.cargo.exception.ModelErrorAttribute.CITY_DIRECTION;
import static com.epam.cargo.exception.WrongInput.NO_EXISTING_DIRECTION;

/**
 * Exception class in a case of missing direction between two cities.
 * @see City
 * @author Roman Kovalchuk
 * @version  1.0
 * */
public class NoExistingDirectionException extends WrongDataException{

    public NoExistingDirectionException(City from, City to, ResourceBundle bundle) {
        super(buildErrorMessage(from, to, bundle));
    }

    /**
     * Constructor for localized error message with rejected City objects.
     * @since 1.0
     **/
    private static String buildErrorMessage(City from, City to, ResourceBundle bundle){
        return String.format(bundle.getString(NO_EXISTING_DIRECTION), from.getName(), to.getName());
    }

    @Override
    public String getModelAttribute() {
        return CITY_DIRECTION.getAttr();
    }
}

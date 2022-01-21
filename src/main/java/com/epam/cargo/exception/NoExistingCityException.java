package com.epam.cargo.exception;

import com.epam.cargo.entity.City;

import java.util.ResourceBundle;

public class NoExistingCityException extends WrongDataException{


    private static final String KEY_MESSAGE_FORMAT = "wrong-input-data.no-existing-city.format";

    public NoExistingCityException(City city, ResourceBundle bundle) {
        super(buildErrorMessage(city, bundle));
    }

    private static String buildErrorMessage(City city, ResourceBundle bundle) {
        return String.format(bundle.getString(KEY_MESSAGE_FORMAT), city.getName());
    }

    @Override
    public String getModelAttribute() {
        return "noExistingCityMessage";
    }

}

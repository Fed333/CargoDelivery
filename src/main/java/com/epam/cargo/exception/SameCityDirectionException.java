package com.epam.cargo.exception;

import java.util.ResourceBundle;

import static com.epam.cargo.exception.WrongInput.INVALID_DIRECTION_SAME_CITIES;

public class SameCityDirectionException extends WrongDataException{

    public SameCityDirectionException(ResourceBundle bundle) {
        super(buildErrorMessage(bundle));
    }

    private static String buildErrorMessage(ResourceBundle bundle) {
        return bundle.getString(INVALID_DIRECTION_SAME_CITIES);
    }

    @Override
    public String getModelAttribute() {
        return "invalidCityDirectionErrorMessage";
    }
}

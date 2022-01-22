package com.epam.cargo.exception;


import java.util.Objects;
import java.util.ResourceBundle;

public class NoValidPasswordException extends WrongDataException{
    private static final String NO_FILLED_PASSWORD_KEY_ERROR_MESSAGE = "wrong-input-data.no-filled-password";
    private static final String NO_VALID_PASSWORD_KEY_ERROR_MESSAGE = "wrong-input-data.no-valid-password";

    public NoValidPasswordException(ResourceBundle bundle, String password) {
        super(getErrorMessage(bundle, password));
    }

    private static String getErrorMessage(ResourceBundle bundle, String password) {
        if (Objects.isNull(password) || password.isBlank()){
            return bundle.getString(NO_FILLED_PASSWORD_KEY_ERROR_MESSAGE);
        }
        return bundle.getString(NO_VALID_PASSWORD_KEY_ERROR_MESSAGE);
    }

    @Override
    public String getModelAttribute() {
        return "noValidPasswordErrorMessage";
    }
}

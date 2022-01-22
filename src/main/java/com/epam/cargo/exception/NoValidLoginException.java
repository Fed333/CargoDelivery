package com.epam.cargo.exception;

import java.util.Objects;
import java.util.ResourceBundle;

public class NoValidLoginException extends WrongDataException{

    private static final String NO_FILLED_LOGIN_KEY_ERROR_MESSAGE = "wrong-input-data.no-filled-login";
    private static final String NO_VALID_LOGIN_KEY_ERROR_MESSAGE = "wrong-input-data.no-valid-login";

    public NoValidLoginException(ResourceBundle bundle, String login) {
        super(getErrorMessage(bundle, login));
    }

    private static String getErrorMessage(ResourceBundle bundle, String login) {
        if (Objects.isNull(login) || login.isBlank()){
            return bundle.getString(NO_FILLED_LOGIN_KEY_ERROR_MESSAGE);
        }
        return bundle.getString(NO_VALID_LOGIN_KEY_ERROR_MESSAGE);
    }

    @Override
    public String getModelAttribute() {
        return "loginErrorMessage";
    }
}

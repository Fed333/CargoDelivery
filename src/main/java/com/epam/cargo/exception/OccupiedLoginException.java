package com.epam.cargo.exception;

import java.util.ResourceBundle;

public class OccupiedLoginException extends WrongDataException{
    private static final String KEY_MESSAGE_FORMAT = "wrong-input-data.occupied-login.format";
    public OccupiedLoginException(ResourceBundle bundle, String login) {
        super(buildErrorMessage(bundle, login));
    }

    private static String buildErrorMessage(ResourceBundle bundle, String login) {
        return String.format(bundle.getString(KEY_MESSAGE_FORMAT), login);
    }

    @Override
    public String getModelAttribute() {
        return "occupiedLoginMessage";
    }
}

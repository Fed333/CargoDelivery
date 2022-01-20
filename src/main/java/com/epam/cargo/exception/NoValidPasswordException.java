package com.epam.cargo.exception;


import java.util.ResourceBundle;

public class NoValidPasswordException extends WrongDataException{
    private static final String KEY_MESSAGE = "wrong-input-data.no-valid-password";
    public NoValidPasswordException(ResourceBundle bundle) {
        super(bundle.getString(KEY_MESSAGE));
    }

    @Override
    public String getModelAttribute() {
        return "noValidPasswordErrorMessage";
    }
}

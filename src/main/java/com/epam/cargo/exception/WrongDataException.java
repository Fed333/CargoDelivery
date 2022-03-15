package com.epam.cargo.exception;

import java.util.ResourceBundle;

/**
 * Common Exception class of wrong user's input.<br>
 * @author Roman Kovalchuk
 * @version 1.0
 * */
abstract public class WrongDataException extends Exception{

    public abstract String getModelAttribute();

    public WrongDataException(String message) {
        super(message);
    }

    public WrongDataException(ResourceBundle bundle, String keyMessage) {
        super(bundle.getString(keyMessage));
    }

}

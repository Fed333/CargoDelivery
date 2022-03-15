package com.epam.cargo.exception;

/**
 * Exception class in a case paying fails.
 * @author Roman Kovalchuk
 * @version  1.0
 * */
public class PayingException extends Exception{

    /**
     * Key of currency in ResourceBundle.
     * */
    public static final String CURRENCY_UAH_KEY = "lang.UAH";

    public PayingException(String message) {
        super(message);
    }

    public String getAttribute(){
        return ModelErrorAttribute.PAYING.getAttr();
    }
}

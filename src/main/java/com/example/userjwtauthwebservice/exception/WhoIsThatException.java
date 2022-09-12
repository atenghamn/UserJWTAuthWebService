package com.example.userjwtauthwebservice.exception;

public class WhoIsThatException extends RuntimeException {
    public WhoIsThatException(String message) {super(message);}
    public WhoIsThatException(String message, Throwable cause) {super(message, cause);}
    public WhoIsThatException(Throwable cause) {super(cause);}

    protected WhoIsThatException(String message, Throwable cause, boolean enableSuppression, boolean writeableStackTrace) {
        super(message, cause, enableSuppression, writeableStackTrace);
    }
}

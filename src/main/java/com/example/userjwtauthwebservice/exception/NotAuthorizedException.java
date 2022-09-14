package com.example.userjwtauthwebservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class NotAuthorizedException extends WhoIsThatException{
    public NotAuthorizedException(String message){
        super(message);
    }
}

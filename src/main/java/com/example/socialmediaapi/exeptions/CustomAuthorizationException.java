package com.example.socialmediaapi.exeptions;

public class CustomAuthorizationException extends ApiException{
    public CustomAuthorizationException(String message, String errorCode) {
        super(message, errorCode);
    }
}

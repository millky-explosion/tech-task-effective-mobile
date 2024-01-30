package com.example.socialmediaapi.exception;

public class CustomAuthorizationException extends ApiException{
    public CustomAuthorizationException(String message, String errorCode) {
        super(message, errorCode);
    }
}

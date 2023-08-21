package com.example.socialmediaapi.exeptions;
import lombok.Getter;

/*
*
*  class ApiException - для работы с кастомными ошибками
*
*/
public class ApiException extends RuntimeException{

    @Getter
    protected String errorCode;

    public ApiException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}

package org.burgerapp.exception;

import lombok.Getter;

@Getter
public class AdressServiceException extends RuntimeException{


    private ErrorType errorType;

    public AdressServiceException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public AdressServiceException(ErrorType errorType, String customMessage) {
        super(customMessage);
        this.errorType = errorType;
    }

}

package org.burgerapp.exception;

import lombok.Getter;

@Getter
public class CartServiceException extends RuntimeException{


    private ErrorType errorType;

    public CartServiceException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public CartServiceException(ErrorType errorType, String customMessage) {
        super(customMessage);
        this.errorType = errorType;
    }

}

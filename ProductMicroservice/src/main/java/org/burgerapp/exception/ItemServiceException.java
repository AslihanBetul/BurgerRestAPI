package org.burgerapp.exception;

import lombok.Getter;

@Getter
public class ItemServiceException extends RuntimeException{


    private ErrorType errorType;

    public ItemServiceException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public ItemServiceException(ErrorType errorType, String customMessage) {
        super(customMessage);
        this.errorType = errorType;
    }

}

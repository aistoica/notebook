package com.ansr.exceptions.common;

import org.springframework.http.HttpStatus;

/**
 * Created by astoica on 9/21/2015.
 */
public enum DefaultErrorCode implements ErrorCode {

    UNEXPECTED_ERROR(HttpStatus.INTERNAL_SERVER_ERROR),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND),
    MISSING_PARAM(HttpStatus.BAD_REQUEST);//MissingServletRequestParam exception

    DefaultErrorCode(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    private HttpStatus httpStatus;

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}

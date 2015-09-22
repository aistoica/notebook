package com.ansr.exceptions;

import com.ansr.exceptions.common.ErrorCode;
import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.*;

/**
 * Created by astoica on 9/21/2015.
 */
public enum UserErrorCodes implements ErrorCode {

    USER_NOT_FOUND(NOT_FOUND);

    private HttpStatus httpStatus;

    UserErrorCodes(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}

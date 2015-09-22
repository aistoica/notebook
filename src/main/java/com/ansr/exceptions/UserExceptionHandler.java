package com.ansr.exceptions;

import com.ansr.controller.UserController;
import com.ansr.exceptions.common.AbstractExceptionHandler;
import com.ansr.exceptions.common.ErrorCode;
import com.ansr.exceptions.common.ErrorContainer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by astoica on 9/21/2015.
 */
@ControllerAdvice(assignableTypes = {UserController.class})
public class UserExceptionHandler extends AbstractExceptionHandler {

    @Override
    public ResponseEntity<ErrorContainer> getResponseEntity(Exception exception, HttpServletRequest request,
                                                            ErrorCode errorCode, Object[] messageArguments, HttpHeaders httpHeaders, HttpStatus httpStatus){
        final ErrorContainer error=new ErrorContainer();
        error.setErrorCode(errorCode);
        error.setErrorMessage(getResourceBundle().getMessage(errorCode.toString(), messageArguments, errorCode.toString(),
                getLocaleResolver().resolveLocale(request)));
        return new ResponseEntity<>(error, httpHeaders, httpStatus);
    }
}

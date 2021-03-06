package com.ansr.exceptions.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * Created by astoica on 9/21/2015.
 */
public class AbstractExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractExceptionHandler.class);

    @Autowired
    private AbstractMessageSource resourceBundle;

    @Autowired
    private LocaleResolver localeResolver;

    protected ResponseEntity<ErrorContainer> getResponseEntity(Exception exception, HttpServletRequest request,
                                                               ErrorCode errorCode, Object[] messageArguments, HttpHeaders httpHeaders, HttpStatus httpStatus) {

        final ErrorContainer error = new ErrorContainer();
        error.setErrorCode(errorCode);
        error.setErrorMessage(getResourceBundle().getMessage(errorCode.toString(), messageArguments, errorCode.toString(),
                this.localeResolver.resolveLocale(request)));
        if (exception.getStackTrace() != null) {
            error.setDeveloperMessage(Arrays.toString(exception.getStackTrace()));
        }
        error.setMoreInfo(exception.getMessage());

        return new ResponseEntity<>(error, httpHeaders, httpStatus);
    }

    protected ResponseEntity<ErrorContainer> getResponseEntity(Exception exception, HttpServletRequest request,
                                                               ErrorCode errorCode, HttpHeaders httpHeaders, HttpStatus httpStatus) {

        return getResponseEntity(exception, request, errorCode, null, httpHeaders, httpStatus);
    }

    protected ResponseEntity<ErrorContainer> getResponseEntity(Exception exception, HttpServletRequest request,
                                                               ErrorCode errorCode, HttpStatus httpStatus) {
        return getResponseEntity(exception, request, errorCode, null, new HttpHeaders(), httpStatus);
    }

    protected ResponseEntity<ErrorContainer> getResponseEntity(Exception exception, HttpServletRequest request,
                                                               ErrorCode errorCode, Object[] messageArguments, HttpStatus httpStatus) {
        return getResponseEntity(exception, request, errorCode, messageArguments, new HttpHeaders(), httpStatus);
    }

    /**
     * This is a fall through exception handling method. If an unknown exception is detected it will be handled here.
     *
     * @param exception exception being caught
     * @param request http request
     */
    @ExceptionHandler
    public ResponseEntity<ErrorContainer> accessDeniedExceptionHandler(AccessDeniedException exception, HttpServletRequest request) {
        LOGGER.error("Authorization exception.", exception);
        return getResponseEntity(exception, request, DefaultErrorCode.UNAUTHORIZED, DefaultErrorCode.UNAUTHORIZED.getHttpStatus());
    }

    /**
     * This is a fall through exception handling method. If an unknown exception is detected it will be handled here.
     *
     * @param exception exception being caught
     * @param request http request
     */
    @ExceptionHandler
    public ResponseEntity<ErrorContainer> exceptionHandler(Exception exception, HttpServletRequest request) {
        LOGGER.error("Unexpected error detected in REST layer.", exception);
        return getResponseEntity(exception, request, DefaultErrorCode.UNEXPECTED_ERROR, DefaultErrorCode.UNEXPECTED_ERROR.getHttpStatus());
    }


    /**
     * This method handles business level exceptions.
     *
     * @param exception business exception being caught
     * @param request http request
     */
    @ExceptionHandler
    public ResponseEntity<ErrorContainer> exceptionHandler(ApplicationException exception, HttpServletRequest request) {
        LOGGER.error("Business level error detected in REST layer.", exception);
        return getResponseEntity(exception, request, exception.getErrorCode(), exception.getArguments(), exception.getErrorCode().getHttpStatus());
    }

    /**
     * This method is called when a validation error occurs.
     * @param exception
     * @param request
     * @param headers
     * @param status
     * @return
     */
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                  HttpHeaders headers, HttpStatus status, HttpServletRequest request) {

        final ErrorContainer error = new ErrorContainer();
        error.setErrorCode(DefaultErrorCode.VALIDATION_ERROR);
        final List<FieldError> errors = exception.getBindingResult().getFieldErrors();
        final StringBuilder message = new StringBuilder();
        final String localizedMessage = getResourceBundle().getMessage(error.getErrorCode().toString(), null,
                this.localeResolver.resolveLocale(request));
        for (final FieldError fieldError : errors) {
            message.append(String.format(localizedMessage, fieldError.getObjectName(), fieldError.getField(), fieldError.getRejectedValue(), fieldError.getDefaultMessage()));
        }
        error.setErrorMessage(message.toString());

        return new ResponseEntity<Object>(error, error.getErrorCode().getHttpStatus());
    }

    protected AbstractMessageSource getResourceBundle() {
        return resourceBundle;
    }

    public void setResourceBundle(AbstractMessageSource resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    protected LocaleResolver getLocaleResolver() {
        return this.localeResolver;
    }

}

package com.ansr.exceptions.common;

import com.ansr.exceptions.common.ErrorCode;

/**
 * Created by astoica on 9/21/2015.
 */
public class ApplicationException extends RuntimeException {

    private static final long serialVersionUID = 1930539361139157688L;

    private ErrorCode errorCode;
    private Object[] arguments;

    private ApplicationException() {
        //hide the default constructor, require the error code to be used
    }

    public ApplicationException(ErrorCode errorCode) {
        super();
        this.setErrorCode(errorCode);
    }

    public ApplicationException(ErrorCode errorCode, Object[] arguments) {
        this(errorCode);
        this.setArguments(arguments);
    }

    public ApplicationException(ErrorCode errorCode, Throwable cause) {
        super(cause);
        this.setErrorCode(errorCode);
    }

    public ApplicationException(ErrorCode errorCode, Object[] arguments, Throwable cause) {
        this(errorCode, cause);
        this.setArguments(arguments);
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public Object[] getArguments() {
        return arguments;
    }

    public void setArguments(Object[] arguments) {
        this.arguments = arguments;
    }
}

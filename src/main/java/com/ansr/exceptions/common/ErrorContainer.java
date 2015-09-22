package com.ansr.exceptions.common;

import com.ansr.exceptions.common.ErrorCode;

/**
 * Created by astoica on 9/21/2015.
 */
public class ErrorContainer {

    private ErrorCode errorCode;
    private String errorMessage;
    private String developerMessage;
    private String moreInfo;

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }

    public String getMoreInfo() {
        return moreInfo;
    }

    public void setMoreInfo(String moreInfo) {
        this.moreInfo = moreInfo;
    }
}

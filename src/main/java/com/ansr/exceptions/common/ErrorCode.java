package com.ansr.exceptions.common;

import org.springframework.http.HttpStatus;

/**
 * Created by astoica on 9/21/2015.
 */
public interface ErrorCode {

    HttpStatus getHttpStatus();
}

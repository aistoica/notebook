package com.ansr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by astoica on 9/21/2015.
 */
public class BaseController {

    @Autowired
    private ReloadableResourceBundleMessageSource resourceBundle;

    @Autowired
    private LocaleResolver localeResolver;

    protected <T> ResponseEntity prepareResponse(T response) {

        return new ResponseEntity<T>(response, HttpStatus.OK);
    }

    protected <T> ResponseEntity prepareResponse(T response, HttpStatus status) {

        return new ResponseEntity<T>(response, status);
    }

    public HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public ReloadableResourceBundleMessageSource getResourceBundle() {
        return resourceBundle;
    }

    public LocaleResolver getLocaleResolver() {
        return localeResolver;
    }
}

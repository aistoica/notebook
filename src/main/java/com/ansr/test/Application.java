package com.ansr.test;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

/**
 * Created by astoica on 8/7/2015.
 */
@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.ansr.repository")
@ComponentScan(basePackages = {"com.ansr"})
public class Application {

    @Bean(autowire = Autowire.BY_TYPE)
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver res = new AcceptHeaderLocaleResolver();
        return res;
    }

    @Bean(autowire = Autowire.BY_TYPE)
    public ReloadableResourceBundleMessageSource resourceBundle() {
        ReloadableResourceBundleMessageSource resourceBundle = new ReloadableResourceBundleMessageSource();
        resourceBundle.setBasename("classpath:locale/messages");
        resourceBundle.setCacheSeconds(5);
        resourceBundle.setDefaultEncoding("UTF-8");

        return resourceBundle;
    }

    public static void main(String... args) {
        SpringApplication.run(Application.class, args);
    }
}

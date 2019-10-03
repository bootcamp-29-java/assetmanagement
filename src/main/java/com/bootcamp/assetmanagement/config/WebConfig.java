package com.bootcamp.assetmanagement.config;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import javax.validation.Validator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @author Lenovo
 */
@Configuration
public class WebConfig implements WebMvcConfigurer{
    
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry)
    {
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/home").setViewName("index");
        registry.addViewController("/track").setViewName("requesttrack");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/ga/home").setViewName("/ga/home");
        registry.addViewController("/admin/home").setViewName("/admin/home");
        registry.addViewController("/manager/home").setViewName("/manager/home");
        registry.addViewController("/user/home").setViewName("/user/home");
    }
    

}

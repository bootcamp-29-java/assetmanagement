/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.assetmanagement.controllers;

import com.bootcamp.assetmanagement.services.EmployeeDummyRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Lenovo
 */
@Controller
public class EmployeeController {
//    @Autowired
//    private EmployeeDummyRest rest;
//    
//    @GetMapping("/asd/{manager}")
//    public String home(@PathVariable(value = "manager") String manager){
//        for (EmployeeDummy empl : rest.getEmployeeDummys(manager)) {
//            System.out.println(empl.getFirstName());
//        }
//        
//        return "login";
//    }
}

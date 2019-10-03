/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.assetmanagement.controllers;

import com.bootcamp.assetmanagement.entities.Employee;
import com.bootcamp.assetmanagement.services.EmployeeService;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Lenovo
 */
@Controller
public class Login {



    @RequestMapping("/")
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.getAuthorities());
        
        return "home";
    }
    
    @RequestMapping("/loginerror")
    public String loginerror(RedirectAttributes redirect) {
        redirect.addFlashAttribute("message", "Invalid Email and Password");
        return "redirect:/login";
    }
    @RequestMapping("/logoutsuccess")
    public String logoutsuccess(RedirectAttributes redirect) {
        redirect.addFlashAttribute("message", "Logout Success");
        return "redirect:/login";
    }
    
    @GetMapping("/login")
    public String handlingLog(){
        String result = "";
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.getName().equalsIgnoreCase("anonymousUser")) {
            result = "redirect:/";
        }else{
            result = "login";
        }
        return result;
    }
}

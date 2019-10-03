/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.assetmanagement.controllers;

import com.bootcamp.assetmanagement.entities.Employee;
import com.bootcamp.assetmanagement.entities.EmployeeDummy;
import com.bootcamp.assetmanagement.services.EmployeeDummyRest;
import com.bootcamp.assetmanagement.services.EmployeeService;
import com.bootcamp.assetmanagement.tools.SendEmail;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Lenovo
 */
@Controller
public class LoginController {

    @Autowired
    private EmployeeService service;
    @Autowired
    private EmployeeDummyRest rest;
    @Autowired
    private SendEmail sendEmail;

    @PostMapping("/verifikasi")
    public String testing(@ModelAttribute(value = "email") String email, @ModelAttribute(value = "password") String password) {
        String result = "";
        EmployeeDummy employee = rest.login(email, password);

        System.out.println(employee.getStatus());
        if (employee.getStatus().equalsIgnoreCase("Berhasil")) {
            int row = service.getByIdLogin(email);
            System.out.println(row);
            if (row == 0) {
                System.out.println("Kesini");
                Employee empl = new Employee();
                empl.setId(employee.getId());
                empl.setEmail(employee.getEmail());
                empl.setFirstName(employee.getFirstName());
                empl.setLastName(employee.getLastName());
                empl.setIsDelete(false);
                empl.setManager(new Employee(employee.getId()));
                service.save(empl);
            } else {
                Employee empl2 = service.getById(employee.getId());
                empl2.setId(employee.getId());
                empl2.setEmail(employee.getEmail());
                empl2.setFirstName(employee.getFirstName());
                empl2.setLastName(employee.getLastName());
                empl2.setLastName(employee.getLastName());
                empl2.setManager(new Employee(employee.getManager()));
                service.save(empl2);
            }
            User user = new User(employee.getId(), "", getAuthorities(employee));
            PreAuthenticatedAuthenticationToken authentication = new PreAuthenticatedAuthenticationToken(user, "", user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            result = "redirect:/";
        } else {
            result = "redirect:/loginerror";
        }
        return result;
    }

    private static Collection<? extends GrantedAuthority> getAuthorities(EmployeeDummy user) {
        String[] roles = user.getRoles();
        final List<SimpleGrantedAuthority> authorities = new LinkedList<>();
        for (String role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }

    @RequestMapping("/testing")
    public String testing() {
        sendEmail.sendEmail("wahyusukses28@gmail.com", "Testing", "testing");

        return "testing";
    }

//    void sendEmail() {
//
//        SimpleMailMessage msg = new SimpleMailMessage();
//        msg.setTo("wahyusukses28@gmail.com");
//
//        msg.setSubject("Testing from Spring Boot");
//        msg.setText("Hello World \n Spring Boot Email");
//
//        javaMailSender.send(msg);
//
//    }
}

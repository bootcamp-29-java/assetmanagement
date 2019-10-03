/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.CobaAssetManagement.controllers;

import com.bootcamp.CobaAssetManagement.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author ASUS
 */
@Controller
public class JasperReportController {

    @Autowired
    private ReportService reportService;

    @RequestMapping("/reportAssetDipinjam")
    public String assetReport() {
        reportService.generateReportDipinjam();
        return "redirect:/reportasset.pdf";
    }
    
    @RequestMapping("/reportAssetReady")
    public String assetReportReady() {
        reportService.generateReportReady();
        return "redirect:/reportready.pdf";
    }
    
//    void sendEmail() {
//        SimpleMailMessage msg = new SimpleMailMessage();
//        msg.setTo("yosuak24@gmail.com");
//
//        msg.setSubject("Testing from Spring Boot");
//        msg.setText("Hello World \n Spring Boot Email");
//        
//        javaMailSender.send(msg);
//    }
    
    

//    @GetMapping("/report2")
//    public String Report(String id) {
//
//        return reportService.generateReport(id);
//    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.assetmanagement.controllers;

import com.bootcamp.assetmanagement.entities.Request;
import com.bootcamp.assetmanagement.entities.RequestStatus;
import com.bootcamp.assetmanagement.repositories.RequestRepository;
import com.bootcamp.assetmanagement.services.RequestService;
import com.bootcamp.assetmanagement.services.RequestStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author Lenovo
 */
@Controller
public class TrackController {
    @Autowired
    private RequestRepository repository;
    @Autowired
    private RequestStatusService service;
    @Autowired
    private RequestService servicereq;

    @GetMapping("/track")
    public String createTrack(String id,Model model){
        System.out.println(id);
        model.addAttribute("requeststatus", service.getTrack(id));
        model.addAttribute("request", servicereq.getById(id));
        System.out.println(servicereq.getById(id));
        for (RequestStatus req : service.getTrack(id)) {
            System.out.println(req.getDateTime());
        }
        
        return "requesttrack";
    }
    
    
    @GetMapping("/trackpick")
    public String trackPick(String id,Model model){
        model.addAttribute("requeststatus", service.getTrack(id));
        model.addAttribute("request", servicereq.getById(id));
        System.out.println(servicereq.getById(id));
        for (RequestStatus req : service.getTrack(id)) {
            System.out.println(req.getDateTime());
        }
        
        return "requesttrack";
    }
}

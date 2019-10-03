/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.assetmanagement.controllers;

import com.bootcamp.assetmanagement.services.RequestItemService;
import com.bootcamp.assetmanagement.services.RequestService;
import javax.persistence.EntityManager;
import javax.persistence.StoredProcedureQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Lenovo
 */
@Controller
public class ReturnController {

    @Autowired
    RequestService requestService;
    @Autowired
    private RequestItemService riService;
    @Autowired
    private EntityManager em;

    @RequestMapping("/assetReturn")
    public String assetReturn(Model model) {
        return "assetReturn";
    }
    @RequestMapping("/returndetail")
    public String returndetail(Model model, String id) {
        model.addAttribute("return", requestService.getById(id));
        model.addAttribute("items", riService.getByReq(id));
        return "admin/returndetail";
    }

    @PostMapping("/detailReturn")
    public String searchIdReturn(Model model,@ModelAttribute(value = "id") String id) {
        System.out.println(id);
        
        return "redirect:/returndetail?id="+id;
    }
    @GetMapping("/returnassetitem")
    public String searchIdReturn(String id,String request) {
        System.out.println(id);
        StoredProcedureQuery spq = this.em.createNamedStoredProcedureQuery("update_request_item");
            spq.setParameter("iditem", id);
            spq.execute();
        return "redirect:/assetReturn";
    }

}

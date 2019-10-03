/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.assetmanagement.controllers;

import com.bootcamp.assetmanagement.entities.ApprovalStatus;
import com.bootcamp.assetmanagement.entities.EmployeeDummy;
import com.bootcamp.assetmanagement.entities.Request;
import com.bootcamp.assetmanagement.entities.RequestStatus;
import com.bootcamp.assetmanagement.repositories.RequestRepository;
import com.bootcamp.assetmanagement.services.RequestItemService;
import com.bootcamp.assetmanagement.services.RequestService;
import com.bootcamp.assetmanagement.tools.SendEmail;
import javax.persistence.EntityManager;
import javax.persistence.StoredProcedureQuery;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Lenovo
 */
@Controller
public class GaController {

    @Autowired
    private RequestRepository repository;
    @Autowired
    private RequestService service;
    @Autowired
    private SendEmail sendEmail;
    @Autowired
    private RequestItemService riService;

    @Autowired
    private EntityManager em;

    @RequestMapping("/ga/needapprove")
    public String managerNeedApprove(Model model) {
        model.addAttribute("requests", service.getByGa());
        return "/ga/needapprove";
    }
    
    @GetMapping("/ga/detailrequest")
    public String managerReqDetail(Model model, String id) {
        model.addAttribute("request", service.getById(id));
        model.addAttribute("items", riService.getByReq(id));
        return "/ga/requestdetail";
    }

    @PostMapping("/confirmga")
    public String confirmManager(@ModelAttribute(value = "note") String note,
            @ModelAttribute(value = "confirm") String conf,
            @ModelAttribute(value = "request") String request, RedirectAttributes redirectAttributes) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.getName());
        System.out.println("Ke GA");
        if (conf.equalsIgnoreCase("1")) {
            StoredProcedureQuery spq = this.em.createNamedStoredProcedureQuery("insert_request_status");
            spq.setParameter("catatan", note);
            spq.setParameter("statusid", "4");
            spq.setParameter("requestid", request);
            spq.setParameter("approver", auth.getName());
            spq.execute();

            Request req = service.getById(request);
            System.out.println(req.getRequester().getEmail());
            System.out.println(req.getRequester().getFirstName());
            sendEmail.sendEmail(req.getRequester().getEmail(), "Confirm Notification!",
                    "Hello " + req.getRequester().getFirstName() +" "+req.getRequester().getLastName()+ ", Your Request Has Been Approved GA \n\n"
                            + "By\n"+req.getCurrentApproval().getFirstName()+" "+req.getCurrentApproval().getLastName()+"\n\n"
                                    + "Note :"+note);
        } else {
            StoredProcedureQuery spq = this.em.createNamedStoredProcedureQuery("insert_request_status");
            spq.setParameter("catatan", note);
            spq.setParameter("statusid", "3");
            spq.setParameter("requestid", request);
            spq.setParameter("approver", auth.getName());
            spq.execute();
            Request req = service.getById(request);
            sendEmail.sendEmail(req.getRequester().getEmail(), "Reject Notification!",
                    "Hello " + req.getRequester().getFirstName() + ", Your Request Has Been Rejected By GA\n\n"
                            + "By\n"+req.getCurrentApproval().getFirstName()+" "+req.getCurrentApproval().getLastName()+"\n\n"
                                    + "Note :"+note);
        }

        redirectAttributes.addFlashAttribute("status", "Confirmasi Berhasil");

        return "redirect:/ga/needapprove";
    }
}

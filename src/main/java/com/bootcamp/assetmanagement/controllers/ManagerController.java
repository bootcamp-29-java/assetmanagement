/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.assetmanagement.controllers;

import com.bootcamp.assetmanagement.entities.ApprovalStatus;
import com.bootcamp.assetmanagement.entities.Employee;
import com.bootcamp.assetmanagement.entities.EmployeeDummy;
import com.bootcamp.assetmanagement.entities.Request;
import com.bootcamp.assetmanagement.entities.RequestStatus;
import com.bootcamp.assetmanagement.repositories.RequestRepository;
import com.bootcamp.assetmanagement.services.EmployeeDummyRest;
import com.bootcamp.assetmanagement.services.EmployeeService;
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
public class ManagerController {

    @Autowired
    private RequestRepository repository;
    @Autowired
    private RequestService service;
    @Autowired
    private SendEmail sendEmail;
    @Autowired
    private EmployeeDummyRest rest;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EntityManager em;
    @Autowired
    private RequestItemService riService;

    @RequestMapping("/manager/needapprove")
    public String managerNeedApprove(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.getName());
        System.out.println(rest.getEmployeeGA());
        model.addAttribute("requests", service.getByManager(auth.getName()));
        return "/manager/needapprove";
    }

    @RequestMapping("/testing2")
    public String testing(Model model) {
        Request req = service.getById("REQ/2019/10/2");
        System.out.println(req.getRequester().getEmail());
//        String email = req.getRequest().getRequester().getEmail();
//        System.out.println(req.getRequest().getRequester().getEmail());
//        sendEmail.sendEmail(email, "Confirm Notification!",
//                "Hello , Your Request Has Been Approved By Your Manager");
        return "testing";
    }

    @GetMapping("/manager/detailrequest")
    public String managerReqDetail(Model model, String id) {
        model.addAttribute("request", service.getById(id));
        model.addAttribute("items", riService.getByReq(id));
        return "/manager/requestdetail";
    }

    @PostMapping("/confirmmanager")
    public String confirmManager(@ModelAttribute(value = "note") String note,
            @ModelAttribute(value = "confirm") String conf,
            @ModelAttribute(value = "request") String request, RedirectAttributes redirectAttributes) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.getName());
        System.out.println(request);
        System.out.println(conf);
        System.out.println(note);
        if (conf.equalsIgnoreCase("1")) {
            StoredProcedureQuery spq = this.em.createNamedStoredProcedureQuery("insert_request_status");
            spq.setParameter("catatan", note);
            spq.setParameter("statusid", "1");
            spq.setParameter("requestid", request);
            spq.setParameter("approver", auth.getName());
            spq.execute();

            Request req = service.getById(request);
            System.out.println(req.getRequester().getEmail());
            System.out.println(req.getRequester().getFirstName());
            sendEmail.sendEmail(req.getRequester().getEmail(), "Confirm Notification!",
                    "Hello " + req.getRequester().getFirstName() +" "+req.getRequester().getLastName()+ ", Your Request Has Been Approved By Your Manager \n\n"
                            + "By\n"+req.getRequester().getManager().getFirstName()+" "+req.getRequester().getManager().getLastName()+"\n\n"
                                    + "Note :"+note);
                for (EmployeeDummy empl : rest.getEmployeeGA()) {
                    sendEmail.sendEmail(empl.getEmail(), "Request Notification!",
                        "Hello , you have 1 new request from " + req.getRequester().getFirstName() +" "+req.getRequester().getLastName());
                }
        } else {
            StoredProcedureQuery spq = this.em.createNamedStoredProcedureQuery("insert_request_status");
            spq.setParameter("catatan", note);
            spq.setParameter("statusid", "2");
            spq.setParameter("requestid", request);
            spq.setParameter("approver", auth.getName());
            spq.execute();
            Request req = service.getById(request);
            sendEmail.sendEmail(req.getRequester().getEmail(), "Reject Notification!",
                    "Hello " + req.getRequester().getFirstName() + ", Your Request Has Been Rejected By Your Manager\n\n"
                            + "By\n"+req.getRequester().getManager().getFirstName()+" "+req.getRequester().getManager().getLastName()+"\n\n"
                                    + "Note :"+note);
        }

        redirectAttributes.addFlashAttribute("status", "Confirmasi Berhasil");

        return "redirect:/manager/needapprove";
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.assetmanagement.controllers;

import com.bootcamp.assetmanagement.entities.ApprovalStatus;
import com.bootcamp.assetmanagement.entities.AssetDetail;
import com.bootcamp.assetmanagement.entities.Cart;
import com.bootcamp.assetmanagement.entities.Employee;
import com.bootcamp.assetmanagement.entities.Request;
import com.bootcamp.assetmanagement.entities.RequestType;
import com.bootcamp.assetmanagement.services.ApprovalStatusService;
import com.bootcamp.assetmanagement.services.AssetDetailService;
import com.bootcamp.assetmanagement.services.AssetService;
import com.bootcamp.assetmanagement.services.CartService;
import com.bootcamp.assetmanagement.services.EmployeeService;
import com.bootcamp.assetmanagement.services.RequestItemService;
import com.bootcamp.assetmanagement.services.RequestService;
import com.bootcamp.assetmanagement.services.RequestTypeService;
import com.bootcamp.assetmanagement.tools.SendEmail;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.StoredProcedureQuery;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author hp
 */
@Controller
public class RequestController {

    @Autowired
    EmployeeService employeeService;
    @Autowired
    RequestService requestService;
    @Autowired
    AssetDetailService assetDetailService;
    @Autowired
    AssetService assetService;
    @Autowired
    ApprovalStatusService approvalStatusService;
    @Autowired
    RequestTypeService requestTypeService;
    @Autowired
    private EntityManager em;
    @Autowired
    private CartService serviceCart;
    @Autowired
    private RequestItemService riService;
    @Autowired
    private SendEmail sendEmail;
    

    @RequestMapping("/createRequest")
    public String getAllRequest(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("assets", assetService.getAll());
        model.addAttribute("carts", serviceCart.getByEmployee(auth.getName()));
        return "createRequest";
    }
    @GetMapping("/deletecart")
    public String deleteCart(int id) {
        System.out.println(serviceCart.getById(id));
        Cart cart = serviceCart.getById(id);
        serviceCart.delete(cart);
        return "redirect:/createRequest";
    }


    @GetMapping("/assetDetail")
    public String getByAsset(Model model, String id) {
        List<AssetDetail> assetDetails = new ArrayList<>();
        for (AssetDetail ad : assetDetailService.getByStatus()) {
            if (ad.getAsset().getId().equalsIgnoreCase(id)) {
                assetDetails.add(ad);
            }
        }
        model.addAttribute("assetdetails", assetDetails);
        return "contentAssetDetail :: assetdetails";
    }

    @RequestMapping(value = "/insertcart", method = RequestMethod.POST)
    public String submit(@ModelAttribute("selectAssetDetail") String AssetDetail, BindingResult result, ModelMap model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        StoredProcedureQuery spq = this.em.createNamedStoredProcedureQuery("insert_cart");
        spq.setParameter("employeeid", auth.getName());
        spq.setParameter("assetid", AssetDetail);
        spq.execute();
        return "redirect:/createRequest";
    }

    @PostMapping("/insertrequest")
    public String addReport(@ModelAttribute(value = "note")String note, RedirectAttributes redirectAttributes) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();        
        StoredProcedureQuery spq = this.em.createNamedStoredProcedureQuery("insert_request");
        spq.setParameter("note", note);
        spq.setParameter("typeid", "REQ");
        spq.setParameter("requesterid", auth.getName());
        spq.setParameter("reportid", "");
        spq.execute();
        Employee manager = employeeService.getById(auth.getName());
        sendEmail.sendEmail(manager.getManager().getEmail(), "Confirm Notification!",
                    "Hello " + manager.getManager().getFirstName() +" "+manager.getManager().getLastName()+ ", You Have 1 New Request \n\n"
                            + "From\n"+manager.getFirstName()+" "+manager.getLastName()+"\n\n"
                                    + "Note :"+note);
        redirectAttributes.addFlashAttribute("status", "Data Berhasil Disimpan");
        return "redirect:/yourrequest";
    }
    

    @RequestMapping("/yourrequest")
    public String request(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("requests", requestService.getByRequester(auth.getName()));
        
        return "request";
    }
    @RequestMapping("/requesthistory")
    public String requesthistory(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("requests", requestService.getHistory(auth.getName()));
        return "requesthistory";
    }
    @GetMapping("/detailrequest")
    public String requestDetail(String id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("request", requestService.getById(id));
        model.addAttribute("items", riService.getByReq(id));
        return "requestdetail";
    }
}

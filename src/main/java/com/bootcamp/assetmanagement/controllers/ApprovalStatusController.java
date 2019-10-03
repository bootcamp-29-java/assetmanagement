/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.assetmanagement.controllers;

import com.bootcamp.assetmanagement.entities.ApprovalStatus;
import com.bootcamp.assetmanagement.entities.RequestType;
import com.bootcamp.assetmanagement.services.ApprovalStatusService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author hp
 */
@Controller
public class ApprovalStatusController {
    
    @Autowired
    ApprovalStatusService service;
    
    @RequestMapping("/approvalStatusHome")
    public String getAll(Model model) {
        model.addAttribute("approvalStatuss", service.getAll());
        return "tb_master/m_approval_status";
    }

    @GetMapping("/editApprovalStatus")
    public String update(Model model) {
        model.addAttribute("approvalStatuss", service.getAll());
        return "tb_master/m_approval_status";
    }

    @PostMapping("/insertApprovalStatus")
    public String insert(@Valid ApprovalStatus approvalStatus, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("status", "Data Gagal Disimpan");
        } else {
            redirectAttributes.addFlashAttribute("status", "Data Berhasil Disimpan");
            service.save(approvalStatus);
        }
        System.out.println(approvalStatus.getId());
        System.out.println(approvalStatus.getName());

        return "redirect:/approvalStatusHome";
    }

    @GetMapping("/deleteApprovalStatus")
    public String delete(String id, RedirectAttributes redirectAttributes) {
        try {
            service.delete(id);
            redirectAttributes.addFlashAttribute("status", "Data Berhasil Dihapus");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("status", "Data Gagal Dihapus");
        }
        return "redirect:/approvalStatusHome";
    }
    
}

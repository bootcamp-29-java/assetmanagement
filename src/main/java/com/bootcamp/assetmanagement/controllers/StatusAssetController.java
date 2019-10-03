/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.assetmanagement.controllers;

import com.bootcamp.assetmanagement.entities.Category;
import com.bootcamp.assetmanagement.entities.StatusAsset;
import com.bootcamp.assetmanagement.services.StatusAssetService;
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
public class StatusAssetController {
    
    @Autowired
    StatusAssetService service;

    @RequestMapping("/statusAssetHome")
    public String getAll(Model model, StatusAsset statusAsset) {
        model.addAttribute("statusAssets", service.getAll());
        return "tb_master/m_status_asset";
    }

    @GetMapping("/editStatusAsset")
    public String update(Model model, StatusAsset statusAsset) {
        model.addAttribute("statusAssets", service.getAll());
        return "tb_master/m_status_asset";
    }

    @PostMapping("/insertStatusAsset")
    public String insert(@Valid StatusAsset statusAsset, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("status", "Data Gagal Disimpan");
        } else {
            redirectAttributes.addFlashAttribute("status", "Data Berhasil Disimpan");
            service.save(statusAsset);
        }
        System.out.println(statusAsset.getId());
        System.out.println(statusAsset.getName());

        return "redirect:/statusAssetHome";
    }

    @GetMapping("/deleteStatusAsset")
    public String delete(String id, RedirectAttributes redirectAttributes) {
        try {
            service.delete(id);
            redirectAttributes.addFlashAttribute("status", "Data Berhasil Dihapus");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("status", "Data Gagal Dihapus");
        }
        return "redirect:/statusAssetHome";
    }
    
}

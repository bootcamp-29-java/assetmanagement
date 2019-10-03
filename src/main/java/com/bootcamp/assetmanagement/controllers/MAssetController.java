/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.assetmanagement.controllers;

import com.bootcamp.assetmanagement.entities.Asset;
import com.bootcamp.assetmanagement.entities.Room;
import com.bootcamp.assetmanagement.services.AssetService;
import com.bootcamp.assetmanagement.services.CategoryService;
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
public class MAssetController {
    
    @Autowired
    AssetService service;
    @Autowired
    CategoryService categoryService;
    
    @RequestMapping("/assetHome")
    public String getAll(Model model) {
        model.addAttribute("assets", service.getAll());
        model.addAttribute("categorys", categoryService.getAll());
        return "tb_master/m_asset";
    }

    @GetMapping("/editAsset")
    public String update(Model model) {
        model.addAttribute("assets", service.getAll());
        model.addAttribute("categorys", categoryService.getAll());
        return "tb_master/m_asset";
    }

    @PostMapping("/insertAsset")
    public String insert(@Valid Asset asset, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("status", "Data Gagal Disimpan");
        } else {
            redirectAttributes.addFlashAttribute("status", "Data Berhasil Disimpan");
            service.save(asset);
        }
        System.out.println(asset.getId());
        System.out.println(asset.getName());
        System.out.println(asset.getCategory().getName());

        return "redirect:/assetHome";
    }

    @GetMapping("/deleteAsset")
    public String delete(String id, RedirectAttributes redirectAttributes) {
        try {
            service.delete(id);
            redirectAttributes.addFlashAttribute("status", "Data Berhasil Dihapus");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("status", "Data Gagal Dihapus");
        }
        return "redirect:/assetHome";
    }
    
}

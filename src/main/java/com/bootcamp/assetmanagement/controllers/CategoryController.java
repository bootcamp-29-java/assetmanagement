/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.assetmanagement.controllers;

import com.bootcamp.assetmanagement.entities.Category;
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
public class CategoryController {

    @Autowired
    CategoryService service;

    @RequestMapping("/categoryHome")
    public String getAll(Model model, Category category) {
        model.addAttribute("categorys", service.getAll());
        return "tb_master/m_category";
    }

    @GetMapping("/editCategory")
    public String update(Model model, Category category) {
        model.addAttribute("categorys", service.getAll());
        return "tb_master/m_category";
    }

    @PostMapping("/insertCategory")
    public String insert(@Valid Category category, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("status", "Data Gagal Disimpan");
        } else {
            redirectAttributes.addFlashAttribute("status", "Data Berhasil Disimpan");
            service.save(category);
        }
        System.out.println(category.getId());
        System.out.println(category.getName());

        return "redirect:/categoryHome";
    }

    @GetMapping("/deleteCategory")
    public String delete(String id, RedirectAttributes redirectAttributes) {
        try {
            service.delete(id);
            redirectAttributes.addFlashAttribute("status", "Data Berhasil Dihapus");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("status", "Data Gagal Dihapus");
        }
        return "redirect:/categoryHome";
    }

}

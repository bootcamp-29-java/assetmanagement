/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.assetmanagement.controllers;

import com.bootcamp.assetmanagement.entities.Asset;
import com.bootcamp.assetmanagement.entities.AssetDetail;
import com.bootcamp.assetmanagement.entities.Category;
import com.bootcamp.assetmanagement.entities.Room;
import com.bootcamp.assetmanagement.entities.StatusAsset;
import com.bootcamp.assetmanagement.services.AssetDetailService;
import com.bootcamp.assetmanagement.services.AssetService;
import com.bootcamp.assetmanagement.services.CategoryService;
import com.bootcamp.assetmanagement.services.RoomService;
import com.bootcamp.assetmanagement.services.StatusAssetService;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.ParameterMode;
import javax.persistence.Persistence;
import javax.persistence.StoredProcedureQuery;
import javax.validation.Valid;
import org.aspectj.lang.annotation.Before;
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
public class AssetController {

    @Autowired
    AssetDetailService assetDetailService;
    @Autowired
    AssetService assetService;
    @Autowired
    StatusAssetService statusAssetService;
    @Autowired
    RoomService roomService;
    @Autowired
    CategoryService categoryService;

    @Autowired
    private EntityManager em;
    
    
    @RequestMapping("/assetdetail")
    public String getAllAsset(Model model) {
        model.addAttribute("assetdetails", assetDetailService.getAll());
        model.addAttribute("assets", assetService.getAll());
        model.addAttribute("statusassets", statusAssetService.getAll());
        model.addAttribute("rooms", roomService.getAll());
        return "/admin/detail-asset";
    }


    @PostMapping("/insertdetailasset")
    public String addAssetDetail(@Valid AssetDetail assetDetail, BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("status", "Data Gagal Disimpan");
        } else {
            if (assetDetail.getId().equalsIgnoreCase("1")) {
                StoredProcedureQuery spq = this.em.createNamedStoredProcedureQuery("insert_detail_asset");
                spq.setParameter("name", assetDetail.getName());
                spq.setParameter("datein", assetDetail.getDateIn());
                spq.setParameter("penaltycost", assetDetail.getPenaltyCost());
                spq.setParameter("asset", assetDetail.getAsset().getId());
                spq.setParameter("status", assetDetail.getStatus().getId());
                spq.execute();
            } else {
                assetDetailService.save(assetDetail);
            }
            redirectAttributes.addFlashAttribute("status", "Data Berhasil Disimpan");
        }
        return "redirect:/assetdetail";
    }
    
}

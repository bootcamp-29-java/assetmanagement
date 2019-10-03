/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.assetmanagement.services;

import com.bootcamp.assetmanagement.entities.AssetDetail;
import com.bootcamp.assetmanagement.repositories.AssetDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@Service
public class AssetDetailService {
    @Autowired
    AssetDetailRepository repository;
    
    public Iterable<AssetDetail> getAll() {
        return repository.findAll();
    }

    public AssetDetail getById(String id){
        return repository.findById(id).get();
    }
    
    public boolean save(AssetDetail assetDetail) {
        if (repository.save(assetDetail) != null) {
            return true;
        } else {
            return false;
        }
    }

    public void delete(String id) {
        repository.deleteById(id);
    }
    
    public Iterable<AssetDetail> getByStatus(){
        return repository.getByStatus();
    }
    
//    public AssetDetail updateStatus(String id, String status){
//        
//    }
    
}

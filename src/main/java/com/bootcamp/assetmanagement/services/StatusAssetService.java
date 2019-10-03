/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.assetmanagement.services;

import com.bootcamp.assetmanagement.entities.StatusAsset;
import com.bootcamp.assetmanagement.repositories.StatusAssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@Service
public class StatusAssetService {
    
    @Autowired
    StatusAssetRepository repository;
    
    public Iterable<StatusAsset> getAll() {
        return repository.findAll();
    }

    public StatusAsset getById(String id){
        return repository.findById(id).get();
    }
    
    public boolean save(StatusAsset statusAsset) {
        if (repository.save(statusAsset) != null) {
            return true;
        } else {
            return false;
        }
    }

    public void delete(String id) {
        repository.deleteById(id);
    }
    
}

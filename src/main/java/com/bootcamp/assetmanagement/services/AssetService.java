/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.assetmanagement.services;

import com.bootcamp.assetmanagement.entities.Asset;
import com.bootcamp.assetmanagement.repositories.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@Service
public class AssetService {
    
    @Autowired
    AssetRepository repository;
    
    public Iterable<Asset> getAll() {
        return repository.findAll();
    }

    public Asset getById(String id){
        return repository.findById(id).get();
    }
    
    public boolean save(Asset asset) {
        if (repository.save(asset) != null) {
            return true;
        } else {
            return false;
        }
    }

    public void delete(String id) {
        repository.deleteById(id);
    }
    
}

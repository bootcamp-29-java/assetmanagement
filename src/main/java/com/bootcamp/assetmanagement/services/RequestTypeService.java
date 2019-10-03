/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.assetmanagement.services;

import com.bootcamp.assetmanagement.entities.RequestType;
import com.bootcamp.assetmanagement.repositories.RequestTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@Service
public class RequestTypeService {
    
    @Autowired
    RequestTypeRepository repository;
    
    public Iterable<RequestType> getAll() {
        return repository.findAll();
    }

    public RequestType getById(String id){
        return repository.findById(id).get();
    }
    
    public boolean save(RequestType requestType) {
        if (repository.save(requestType) != null) {
            return true;
        } else {
            return false;
        }
    }

    public void delete(String id) {
        repository.deleteById(id);
    }
    
}

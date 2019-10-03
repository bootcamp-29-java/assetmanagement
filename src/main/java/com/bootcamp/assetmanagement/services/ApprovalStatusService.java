/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.assetmanagement.services;

import com.bootcamp.assetmanagement.entities.ApprovalStatus;
import com.bootcamp.assetmanagement.repositories.ApprovalStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@Service
public class ApprovalStatusService {
    
    @Autowired
    ApprovalStatusRepository repository;
    
    public Iterable<ApprovalStatus> getAll() {
        return repository.findAll();
    }

    public ApprovalStatus getById(String id){
        return repository.findById(id).get();
    }
    
    public boolean save(ApprovalStatus approvalStatus) {
        if (repository.save(approvalStatus) != null) {
            return true;
        } else {
            return false;
        }
    }

    public void delete(String id) {
        repository.deleteById(id);
    }
    
}

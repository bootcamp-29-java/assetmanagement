/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.assetmanagement.services;

import com.bootcamp.assetmanagement.entities.RequestStatus;
import com.bootcamp.assetmanagement.repositories.RequestStatusRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@Service
public class RequestStatusService {
    
    @Autowired
    RequestStatusRepository repository;
    
    public Iterable<RequestStatus> getAll() {
        return repository.findAll();
    }

    public RequestStatus getById(String id){
        return repository.findById(id).get();
    }
    
    public boolean save(RequestStatus requestStatus) {
        if (repository.save(requestStatus) != null) {
            return true;
        } else {
            return false;
        }
    }
    public List<RequestStatus> getTrack(String request){
        return repository.getTrack(request);
    }
    public void delete(String id) {
        repository.deleteById(id);
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.assetmanagement.services;

import com.bootcamp.assetmanagement.entities.Request;
import com.bootcamp.assetmanagement.entities.RequestStatus;
import com.bootcamp.assetmanagement.repositories.RequestRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@Service
public class RequestService {
    
    @Autowired
    RequestRepository repository;
    
    public Iterable<Request> getAll() {
        return repository.findAll();
    }
    
    public List<Request> getByRequester(String requester){
        return repository.getByRequester(requester);
    }
    public List<Request> getHistory(String requester){
        return repository.getHistory(requester);
    }

    public List<Request> getByManager(String manager){
        return repository.getByManager(manager);
    }
    
    public List<Request> getByGa(){
        return repository.getByGA();
    }
    
    
    
    public Request getById(String id){
        return repository.findById(id).get();
    }
    
    public boolean save(Request request) {
        if (repository.save(request) != null) {
            return true;
        } else {
            return false;
        }
    }

    public void delete(String id) {
        repository.deleteById(id);
    }
    
}

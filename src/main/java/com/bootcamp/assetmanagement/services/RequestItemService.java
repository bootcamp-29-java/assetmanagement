/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.assetmanagement.services;

import com.bootcamp.assetmanagement.entities.RequestItem;
import com.bootcamp.assetmanagement.repositories.RequestItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Lenovo
 */
@Service
public class RequestItemService {
    @Autowired
    private RequestItemRepository repository;
    
    
    public Iterable<RequestItem> getByReq(String req){
        return repository.getByRequester(req);
    }
}

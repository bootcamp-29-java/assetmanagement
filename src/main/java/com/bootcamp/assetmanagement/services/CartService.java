/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.assetmanagement.services;

import com.bootcamp.assetmanagement.entities.Cart;
import com.bootcamp.assetmanagement.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Lenovo
 */
@Service
public class CartService {
 
    @Autowired
    private CartRepository repository;
    
    public Iterable<Cart> getByEmployee(String employee){
        return repository.getByEmployee(employee);
    }
    
    public Cart getById(int id){
        return repository.delete(id);
    }
    
    public void delete(Cart cart){
        repository.delete(cart);
    }
    
}

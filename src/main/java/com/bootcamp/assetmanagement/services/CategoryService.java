/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.assetmanagement.services;

import com.bootcamp.assetmanagement.entities.Category;
import com.bootcamp.assetmanagement.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@Service
public class CategoryService {
    
    @Autowired
    CategoryRepository repository;
    
    public Iterable<Category> getAll() {
        return repository.findAll();
    }

    public Category getById(String id){
        return repository.findById(id).get();
    }
    
    public boolean save(Category category) {
        if (repository.save(category) != null) {
            return true;
        } else {
            return false;
        }
    }

    public void delete(String id) {
        repository.deleteById(id);
    }
    
}

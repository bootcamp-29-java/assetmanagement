/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.assetmanagement.services;

import com.bootcamp.assetmanagement.entities.Employee;
import com.bootcamp.assetmanagement.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@Service
public class EmployeeService {
    
    @Autowired
    EmployeeRepository repository;
    
    public Iterable<Employee> getAll() {
        return repository.findAll();
    }
    public Iterable<Employee> getByGa(String role) {
        return repository.getByGA(role);
    }

    public Employee getById(String id){
        return repository.findById(id).get();
    }
    
    public boolean save(Employee employee) {
        if (repository.save(employee) != null) {
            return true;
        } else {
            return false;
        }
    }
    public int getByIdLogin(String id) {
        return repository.getByIdLogin(id);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }
    
}

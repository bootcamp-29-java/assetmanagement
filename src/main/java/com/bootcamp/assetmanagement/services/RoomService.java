/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.assetmanagement.services;

import com.bootcamp.assetmanagement.entities.Room;
import com.bootcamp.assetmanagement.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@Service
public class RoomService {
    
    @Autowired
    RoomRepository repository;
    
    public Iterable<Room> getAll() {
        return repository.findAll();
    }

    public Room getById(String id){
        return repository.findById(id).get();
    }
    
    public boolean save(Room room) {
        if (repository.save(room) != null) {
            return true;
        } else {
            return false;
        }
    }

    public void delete(String id) {
        repository.deleteById(id);
    }
    
}

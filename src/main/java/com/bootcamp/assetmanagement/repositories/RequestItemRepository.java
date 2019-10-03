/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.assetmanagement.repositories;

import com.bootcamp.assetmanagement.entities.RequestItem;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Lenovo
 */
@Repository
public interface RequestItemRepository extends CrudRepository<RequestItem, String>{
    @Query(value = "SELECT * FROM TB_TR_Request_Item WHERE request = ?1", nativeQuery = true)
    public List<RequestItem> getByRequester(String request);
}

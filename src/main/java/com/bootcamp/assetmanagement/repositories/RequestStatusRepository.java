/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.assetmanagement.repositories;

import com.bootcamp.assetmanagement.entities.RequestStatus;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author hp
 */
@Repository
public interface RequestStatusRepository extends CrudRepository<RequestStatus, String>{
    @Query(value = "SELECT * FROM tb_tr_request_status WHERE request = ?1", nativeQuery = true)
    public List<RequestStatus> getTrack(String request);
}

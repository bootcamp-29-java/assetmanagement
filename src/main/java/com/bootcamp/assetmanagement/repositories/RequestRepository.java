/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.assetmanagement.repositories;

import com.bootcamp.assetmanagement.entities.Request;
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
public interface RequestRepository extends CrudRepository<Request, String>{
    
    @Query(value = "SELECT * FROM TB_TR_Request WHERE current_status != '4' AND current_status != '5' AND current_status != '6' AND requester = ?1", nativeQuery = true)
    public List<Request> getByRequester(String requester);
    
    @Query(value = "SELECT * FROM TB_TR_Request WHERE current_status = '4' OR current_status = '5' OR current_status = '6'  AND requester = ?1", nativeQuery = true)
    public List<Request> getHistory(String requester);
    
    @Query(value = "SELECT r.* FROM tb_tr_request r JOIN tb_m_employee e ON r.requester = e.id WHERE r.current_status = '0' AND e.manager = ?1", nativeQuery = true)
    public List<Request> getByManager(String manager);

    @Query(value = "SELECT * FROM tb_tr_request WHERE current_status = '1'", nativeQuery = true)
    public List<Request> getByGA();
    
    
    
}

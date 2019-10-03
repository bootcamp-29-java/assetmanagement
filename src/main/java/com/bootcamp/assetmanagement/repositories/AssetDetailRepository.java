/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.assetmanagement.repositories;

import com.bootcamp.assetmanagement.entities.AssetDetail;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author hp
 */
@Repository
public interface AssetDetailRepository extends CrudRepository<AssetDetail, String>{
    
    @Query(value = "SELECT * FROM TB_TR_Asset_Detail WHERE status = 3", nativeQuery = true)
    public List<AssetDetail> getByStatus();
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.assetmanagement.repositories;

import com.bootcamp.assetmanagement.entities.Cart;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Lenovo
 */
@Repository
public interface CartRepository extends JpaRepository<Cart, String> {
    @Query(value = "SELECT * FROM tb_tr_cart WHERE employee = ?1", nativeQuery = true)
    public List<Cart> getByEmployee(String employee);
    @Query(value = "SELECT * FROM tb_tr_cart WHERE id = ?1", nativeQuery = true)
    public Cart delete(int id);
}

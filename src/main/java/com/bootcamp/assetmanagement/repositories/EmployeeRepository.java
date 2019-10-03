/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.assetmanagement.repositories;

import com.bootcamp.assetmanagement.entities.Employee;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author hp
 */
@Repository
public interface EmployeeRepository extends CrudRepository<Employee, String>{
    
    @Query(value = "SELECT * FROM TB_M_Employee WHERE email = ?1", nativeQuery = true)
    public Employee getByEmail(String email);
    @Query(value = "SELECT e.email FROM TB_M_Employee e JOIN TB_TR_Employee_Role rl ON e.id = rl.employee WHERE rl.role = ?1", nativeQuery = true)
    public List<Employee> getByGA(String role);
    @Query(value = "SELECT Count(*) FROM TB_M_Employee WHERE email = ?1", nativeQuery = true)
    public int getByIdLogin(String id);
    
}

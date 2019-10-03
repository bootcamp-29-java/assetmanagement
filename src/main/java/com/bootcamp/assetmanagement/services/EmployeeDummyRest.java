/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.assetmanagement.services;

import com.bootcamp.assetmanagement.entities.EmployeeDummy;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Lenovo
 */
@Service
public class EmployeeDummyRest{
    private static RestTemplate restTemplate = new RestTemplate();
    
    public List<EmployeeDummy> getEmployeeGA(){
        String url = "http://localhost:8084/employee/ga";
        
        ResponseEntity<List<EmployeeDummy>> re = restTemplate.exchange(url, HttpMethod.GET, null, 
                new ParameterizedTypeReference<List<EmployeeDummy>>(){});
        List<EmployeeDummy> employeeDummys = re.getBody();
        return employeeDummys;
    }
    
    public EmployeeDummy login(String email, String password){
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("email", email);
        params.add("password", password);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        final HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(params, headers);
        ResponseEntity<EmployeeDummy> responseEntity = restTemplate.exchange("http://localhost:8084/login/assetmanagement/", HttpMethod.POST, entity, EmployeeDummy.class);
        EmployeeDummy result = responseEntity.getBody();
        return result;
//        String url = "http://localhost:8084/login/{email}/{password}";
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("email", email);
//        map.put("password", password);
//        
//        EmployeeDummy employeeDummy = restTemplate.getForObject(url, EmployeeDummy.class, map);
//        return employeeDummy;
    }
   
}

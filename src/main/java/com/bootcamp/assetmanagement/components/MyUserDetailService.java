package com.bootcamp.assetmanagement.components;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.bootcamp.assetmanagement.entities.Employee;
import com.bootcamp.assetmanagement.entities.EmployeeDummy;
import com.bootcamp.assetmanagement.repositories.EmployeeRepository;
import com.bootcamp.assetmanagement.services.EmployeeService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Lenovo
 */
@Service
@Transactional
public class MyUserDetailService implements UserDetailsService{

    @Resource
    private EmployeeRepository repository;
    private static RestTemplate restTemplate = new RestTemplate();
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        EmployeeDummy login = getById(email,"admin");
        if (login == null) {
            throw new UsernameNotFoundException("User dengan email" + email + " tidak ditemukan");
        }
        return new User(login.getEmail(), "admin", getAuthorities(login));
    }

    private static Collection<? extends GrantedAuthority> getAuthorities(EmployeeDummy user) {
        String[] roles = user.getRoles();
        Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(roles);
        return authorities;
    }

    public static EmployeeDummy getById(String param, String pass) {
        String url = "http://localhost:8084/login/{email}/{password}";
        Map<String, String> map = new HashMap<String, String>();
        map.put("email", param);
        map.put("password", pass);
        EmployeeDummy login = restTemplate.getForObject(url, EmployeeDummy.class, map);
        return login;
    }
}

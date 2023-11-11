
package com.ns.CrimeManagement.repository;
import com.ns.CrimeManagement.model.*;
import com.ns.CrimeManagement.controller.*;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LoginRepository extends JpaRepository<LoginModel,Integer> {
    LoginModel findByUsernameAndPassword(String username,String password);}
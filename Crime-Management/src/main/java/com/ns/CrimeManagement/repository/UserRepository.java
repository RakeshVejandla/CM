
package com.ns.CrimeManagement.repository;
import com.ns.CrimeManagement.model.*;
import com.ns.CrimeManagement.controller.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<UserModel,Integer> {
    Optional<UserModel> findByUsernameAndPassword(String username,String password);
    Optional<UserModel> findByUsername(String username);
}
package com.ns.CrimeManagement.repository;

import com.ns.CrimeManagement.model.AdminModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<AdminModel,Integer> {
    Optional<AdminModel> findByUsernameAndPassword(String username, String password);
//    AdminModel findByUsernameAndPassword(String username,String password);

    Optional<AdminModel> findByUsername(String username);
}

package com.ns.CrimeManagement.repository;

import com.ns.CrimeManagement.model.LoginModel;
import com.ns.CrimeManagement.model.PoliceModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PoliceRepository extends JpaRepository<PoliceModel,Integer> {

    PoliceModel deleteByPoliceid(String policeid);
    Optional<PoliceModel> findByPoliceid(String policeid);
    Optional<PoliceModel> findByUsernameAndPassword(String username, String password);
    Optional<PoliceModel> findByUsername(String username);
//    PoliceModel findByUsername(String username);
}

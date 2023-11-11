package com.ns.CrimeManagement.repository;

import com.ns.CrimeManagement.model.MissingModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissingRepository extends JpaRepository<MissingModel,Integer> {
}

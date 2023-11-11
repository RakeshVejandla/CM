package com.ns.CrimeManagement.repository;

import com.ns.CrimeManagement.model.WantedModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WantedRepository extends JpaRepository<WantedModel,Integer> {
}

package com.ns.CrimeManagement.repository;

import com.ns.CrimeManagement.model.ComplaintModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComplaintRepository  extends JpaRepository<ComplaintModel,Integer> {

//    ComplaintModel findByUsername(String username);
    List<ComplaintModel> findByAssignedto(String policeid);
    List<ComplaintModel> findByUsername(String username);

}

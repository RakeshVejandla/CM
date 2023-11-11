package com.ns.CrimeManagement.repository;

import com.ns.CrimeManagement.model.FeedbackModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<FeedbackModel,Integer> {
}

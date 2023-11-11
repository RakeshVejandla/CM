package com.ns.CrimeManagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ns.CrimeManagement.Exception.RecordNotFoundException;
import com.ns.CrimeManagement.model.*;
import com.ns.CrimeManagement.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/police")
public class PoliceController {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    ComplaintRepository complaintRepository;

    @Autowired
    MissingRepository missingRepository;

    @Autowired
    PoliceRepository policeRepository;

    @Autowired
    WantedRepository wantedRepository;

    @Autowired
    FeedbackRepository feedbackRepository;

    @GetMapping("/policemembers")
    public List<PoliceModel> getpolice() {
        List<PoliceModel> s = policeRepository.findAll();
        return s;
    }

    @PostMapping("/policeassignedcases")
    public List<ComplaintModel> getcases(@RequestBody String name) {
        Optional<PoliceModel> s = policeRepository.findByUsername(name);
        List<ComplaintModel> s1 = new ArrayList<>();
        if (s.isPresent()) {
            System.out.println(s.get().getPoliceid());
            s1 = complaintRepository.findByAssignedto(s.get().getPoliceid());
            System.out.println(s1.get(0).getStatus());
            return s1;
        }
        s1.clear();
        return s1;
    }


    @PutMapping("/statusupdation/{id}")
    public void updatestatus(@PathVariable("id") Integer id,@RequestBody String status) {
        System.out.println("dfsdfsd");
        String sql = "update cases set status=? where id=?";
        jdbcTemplate.update(sql,status,id);
    }

    @PostMapping("/policelogin")
    public Optional<PoliceModel> getPolice(@RequestBody PoliceModel policeModel) {
        Optional<PoliceModel> customer = policeRepository.findByUsernameAndPassword(policeModel.getUsername(), policeModel.getPassword());
        System.out.println("Get all Polices...");
        if(customer.isPresent()){
        return customer;}
        else
        {
            throw new RecordNotFoundException("No Record is present");
        }
    }

    @PostMapping("policepasswordupdation")
    public Optional<PoliceModel> updatepassword(@RequestBody PoliceModel policeModel) {
        Optional<PoliceModel> s = policeRepository.findByUsername(policeModel.getUsername());
        if (s.isPresent()) {
            String sql = "update user set password=? where username=?";
            jdbcTemplate.update(sql, policeModel.getPassword(), policeModel.getUsername());
            return s;
        }
        return s;
    }
}

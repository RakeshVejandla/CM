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
@RequestMapping("/api/admin")
public class CrimeController {

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

    @PostMapping("/adminlogin")
    public Optional<AdminModel> getAdmins(@RequestBody AdminModel adminModel) {
        Optional<AdminModel> customer = adminRepository.findByUsernameAndPassword(adminModel.getUsername(), adminModel.getPassword());
        System.out.println("Get all Admins...");
        if(customer.isPresent()){
        return customer;}
        else{
            throw new RecordNotFoundException("No Record is Present");
        }
    }


    @PostMapping("/adminregister")
    public AdminModel adminRegister(@RequestBody AdminModel adminmodel) {
        String addr = adminmodel.getAddress1() + "," + adminmodel.getCity() + "," + adminmodel.getState() + "," + adminmodel.getCountry() + "," + adminmodel.getPincode();
        adminmodel.setAddress1(addr);
        Optional<AdminModel> customer1 = adminRepository.findByUsernameAndPassword(adminmodel.getAdminusername(), adminmodel.getAdminpassword());
        System.out.println(customer1.isPresent());
        if (customer1.isPresent()) {
            AdminModel customer = adminRepository.save(adminmodel);
            System.out.println(customer);
            return customer;
        }
        System.out.println("Admin Registered Succsessfully");
        return adminmodel;
    }


    @PostMapping("/policejoining")
    public PoliceModel addpolice(@RequestBody PoliceModel policeModel) {
        String addr = policeModel.getAddress() + "," + policeModel.getCity() + "," + policeModel.getState() + "," + policeModel.getCountry() + "," + policeModel.getPincode();
        policeModel.setAddress(addr);
        System.out.println("police joining");
        PoliceModel customer = policeRepository.save(policeModel);
        return customer;
    }

    @PostMapping("/removepolice")
    public Optional<PoliceModel> deletepolice(@RequestBody PoliceModel policeModel) {
        System.out.println("wewrwr");
        Optional<PoliceModel> customer = policeRepository.findByPoliceid(policeModel.getPoliceid());

        if (customer.isPresent()) {
            System.out.println("cbvbvn");
            System.out.println(customer.get().getId());
            policeRepository.deleteById(customer.get().getId());
            return customer;
        }
        System.out.println("dfgfdgfdg");
        return customer;
    }

    @GetMapping("/managecases")
    public List<ComplaintModel> getcustomers() {
        List<ComplaintModel> customer = complaintRepository.findAll();
        System.out.println(customer.get(0).getAssignedto());
        return customer;
    }

    @PostMapping("/policeassigned")
    public Optional<PoliceModel> getcustomers(@RequestParam Map<Integer, String> allparams) {
        PoliceModel policeModel = new PoliceModel();
        Optional<PoliceModel> s = policeRepository.findByPoliceid(allparams.get("assignedto"));
        if (s.isPresent()) {
            String sql = "update cases set assignedto=? where id=?";
            jdbcTemplate.update(sql, allparams.get("assignedto"), allparams.get("id"));
            return s;
        }
        return s;
    }

    @GetMapping("/managemissing")
    public List<MissingModel> getmissing() {
        List<MissingModel> customer = missingRepository.findAll();
        return customer;
    }

    @GetMapping("/managewanted")
    public List<WantedModel> getwanted() {
        List<WantedModel> customer = wantedRepository.findAll();
        return customer;
    }

    @GetMapping("/feedbackwanted")
    public List<FeedbackModel> getfeedback() {
        List<FeedbackModel> s = feedbackRepository.findAll();
        return s;
    }

    @PostMapping("/removemissing")
    public List<MissingModel> removemissing(@RequestBody String name) {
        String sql = "delete from missing where name=?";
        jdbcTemplate.update(sql, name);
        List<MissingModel> s = missingRepository.findAll();
        return s;
    }

    @PostMapping("/removewanted")
    public void removewanted(@RequestBody String name) {
        String sql = "delete from mostwanted where name=?";
        jdbcTemplate.update(sql, name);
    }




    @PostMapping("adminpasswordupdation")
    public Optional<AdminModel> updatepassword(@RequestBody AdminModel adminModel) {
        Optional<AdminModel> s = adminRepository.findByUsername(adminModel.getUsername());
        if (s.isPresent()) {
            String sql = "update user set password=? where username=?";
            jdbcTemplate.update(sql, adminModel.getPassword(), adminModel.getUsername());
            return s;
        }
        return s;
    }



    @PostMapping("/removecases")
    public ComplaintModel updatepassword(@RequestBody ComplaintModel complaintModel) {
        String sql = "delete from cases where id=?";
        jdbcTemplate.update(sql, complaintModel.getId());
        return complaintModel;
    }

    @PostMapping("/missingimagestoring")
    public List<MissingModel> addmissingimage(@RequestParam ("image") MultipartFile file,@RequestParam("data")String data) throws IOException{
    MissingModel s=new ObjectMapper().readValue(data,MissingModel.class);
    s.setImage(file.getBytes());
    missingRepository.save(s);
    List<MissingModel> s1=new ArrayList<>();
    s1=missingRepository.findAll();
    return s1;
    }

    @PostMapping("/wantedimagestoring")
    public List<WantedModel> addwantedimage(@RequestParam ("image") MultipartFile file,@RequestParam("data")String data) throws IOException{
        WantedModel s=new ObjectMapper().readValue(data,WantedModel.class);
        s.setImage(file.getBytes());
        System.out.println("dfgsdfghfgf");
        wantedRepository.save(s);
        List<WantedModel> s1=new ArrayList<>();
        s1=wantedRepository.findAll();
        System.out.println("dfsdsd");
        return s1;
    }
}

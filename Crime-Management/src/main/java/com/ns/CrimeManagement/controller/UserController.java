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
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
ComplaintRepository complaintRepository;

    @Autowired
    WantedRepository wantedRepository;

    @Autowired
    MissingRepository missingRepository;

    @Autowired
    FeedbackRepository feedbackRepository;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @PostMapping("/visitorlogin")
    public Optional<UserModel> getVisitor(@RequestBody UserModel userModel) throws Exception {
        Optional<UserModel> customer = userRepository.findByUsernameAndPassword(userModel.getUsername(), userModel.getPassword());
        System.out.println("Get all Visitors...");
        if(customer.isPresent()){return customer;}
        else{
            throw new RecordNotFoundException("No Record Is Present");
        }

    }

    @PostMapping("/userregister")
    public UserModel userRegister(@RequestBody UserModel usermodel) {
        String addr = usermodel.getAddress1() + "," + usermodel.getCity() + "," + usermodel.getState() + "," + usermodel.getCountry() + "," + usermodel.getPincode();
        usermodel.setAddress1(addr);
        UserModel customer = userRepository.save(usermodel);
        System.out.println("User Registered Succsessfully");
        return customer;
    }


    @PostMapping("/complaintimage")
    public ComplaintModel insertimage(@RequestParam("image") MultipartFile file, @RequestParam("data") String data) throws IOException {
        ComplaintModel image = new ObjectMapper().readValue(data, ComplaintModel.class);
        image.setImage(file.getBytes());
        complaintRepository.save(image);
        return image;
    }

    @GetMapping("/showimage/{username}")
    public List<ComplaintModel> getimage(@PathVariable("username") String data) {
        List<ComplaintModel> image = complaintRepository.findByUsername(data);
        return image;
    }

    @GetMapping("/showimages")
    public List<WantedModel> wanted() {
        List<WantedModel> customer = wantedRepository.findAll();
        return customer;
    }

    @GetMapping("/missingimages")
    public List<MissingModel> missing() {
        List<MissingModel> customer = missingRepository.findAll();
        return customer;
    }

    @PostMapping("/feedback")
    public FeedbackModel givefeedback(@RequestBody FeedbackModel feedbackModel) {
        FeedbackModel s = feedbackRepository.save(feedbackModel);
        return s;
    }

    @PostMapping("/openfiruserdata")
    public List<ComplaintModel> openfiruser(@RequestBody String name) {
        List<ComplaintModel> s=complaintRepository.findByUsername(name);
        return s;
    }

    @PostMapping("/visitorpasswordupdation")
    public Optional<UserModel> updatepassword(@RequestBody UserModel userModel) {
        Optional<UserModel> s = userRepository.findByUsername(userModel.getUsername());
        if (s.isPresent()) {
            String sql = "update user set password=? where username=?";
            jdbcTemplate.update(sql, userModel.getPassword(), userModel.getUsername());
            return s;
        }
        return s;
    }

    @PostMapping("/edit-information")
    public Optional<UserModel> getuserinformation(@RequestBody String name){
        Optional<UserModel> s=userRepository.findByUsername(name);
        return s;
        }

}

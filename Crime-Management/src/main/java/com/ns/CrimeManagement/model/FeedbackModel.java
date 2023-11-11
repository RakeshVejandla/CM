package com.ns.CrimeManagement.model;


import javax.persistence.*;

@Entity
@Table(name="feedback")
public class FeedbackModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Integer Id;
    private String username1;
    private String matter;
    private String receivedby;
    private Integer rating;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getUsername1() {
        return username1;
    }

    public void setUsername1(String username1) {
        this.username1 = username1;
    }

    public String getMatter() {
        return matter;
    }

    public void setMatter(String matter) {
        this.matter = matter;
    }

    public String getReceivedby() {
        return receivedby;
    }

    public void setReceivedby(String receivedby) {
        this.receivedby = receivedby;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}

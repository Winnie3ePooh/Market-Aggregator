package com.netcracker.sa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @NotNull
    private String imageURL = "null";

    @ManyToOne
    @JoinColumn(name = "good_id")
    @JsonIgnore
    private Good good;

    public Image(){};

    public Image(String imageURL){
        this.imageURL = imageURL;
    };

    public Long getId() {
        return id;
    }

    public String getImageURL() {
        return imageURL;
    }


    @Transactional
    public Good getGood() {
        return good;
    }
    @Transactional
    public void setGood(Good good) {
        this.good = good;
    }

    public String toString() {
        return "Image [id=" + this.id +
                ", name=" + this.imageURL + "]";
    }


}

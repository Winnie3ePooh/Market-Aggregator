package com.netcracker.sa.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "goods")
public class Good {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @NotNull
    private String title;
    @NotNull
    private String description;
    @NotNull
    private String creationDate;
    @NotNull
    private Float cost;
    @NotNull
    private String imageURL = "null";
    @NotNull
    private String url;
//
//    @NotNull
//    @ManyToOne
//    @JoinColumn(name = "shop_id")
//    private Shop shop;

    @ManyToOne
    @JoinColumn(name = "subcategory_id")
    private Subcategory subcategory;

    public Good(){};

    public Good(String title, String description, String creationDate, Float cost, String imageURL,
                String url, Subcategory subcategory){
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.cost = cost;
        this.imageURL = imageURL;
        this.url = url;
        this.subcategory = subcategory;

    };

}

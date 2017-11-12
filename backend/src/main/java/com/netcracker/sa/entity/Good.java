package com.netcracker.sa.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "goods")
public class Good {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @NotNull
    private String itemID;
    @NotNull
    private String title;
    @NotNull
    private String description;
    @NotNull
    private LocalDate creationDate;
    @NotNull
    private LocalDate endDate;
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

    public Good(String itemID, String title, String description, String creationDate, String endDate,
                Float cost, String imageURL,
                String url, Subcategory subcategory){
        this.itemID = itemID;
        this.title = title;
        this.description = description;
        this.creationDate = LocalDate.parse(creationDate);
        this.endDate = LocalDate.parse(endDate);
        this.cost = cost;
        this.imageURL = imageURL;
        this.url = url;
        this.subcategory = subcategory;
    };

    public long getId() {
        return id;
    }
    public String  getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public Float getCost() {
        return cost;
    }
    public String getImageURL() {
        return imageURL;
    }
    public String getUrl() {
        return url;
    }

    public Subcategory getSubcategory() {
        return subcategory;
    }

    public String toString() {
        return "Good [id=" + this.id +
                ", name=" + this.title + "]";
    }


}

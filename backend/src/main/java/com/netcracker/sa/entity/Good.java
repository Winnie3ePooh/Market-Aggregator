package com.netcracker.sa.entity;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "goods")
public class Good {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @NotNull
    private String uri;
    @NotNull
    private String title;
    @NotNull
    private String description;
    @NotNull
    private LocalDate creationDate;
    @NotNull
    private LocalDate endDate;
    @NotNull
    private Float price;
    @NotNull
    private String url;

    @ManyToOne
    @JoinColumn(name = "subcategory_id")
    private Subcategory subcategory;

    @OneToMany(mappedBy = "good")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Image> images;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    public Good(){};

    public Good(String uri, String title, String description, String creationDate, String endDate,
                Float price,
                String url, Subcategory subcategory, Shop shop){
        this.uri = uri;
        this.title = title;
        this.description = description;
        this.creationDate = LocalDate.parse(creationDate);
        this.endDate = LocalDate.parse(endDate);
        this.price = price;
        this.url = url;
        this.subcategory = subcategory;
        this.shop = shop;
    };

    public Long getId() {
        return id;
    }
    public String getUri() {
        return  uri;
    }
    public String  getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public Float getCost() {
        return price;
    }
    public String getUrl() {
        return url;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Subcategory getSubcategory() {
        return subcategory;
    }
    public Shop getShop() {return shop; };

    @Transactional
    public List<Image> getImages() {
        return images;
    }

    @Transactional
    public void setImages(List<Image> images) {
        this.getImages().addAll(images);
    }


    public String toString() {
        return "Good [id=" + this.id +
                ", name=" + this.title + "]";
    }

}

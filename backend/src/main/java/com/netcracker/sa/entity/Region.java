package com.netcracker.sa.entity;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "regions")
public class Region {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @NotNull
    private String name;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "regions")
    private List<Shop> shops;

    public Region() {};

    public  Region(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    //@Transactional
    public List<Shop> getShops() {
        return shops;
    }

    //@Transactional
    public void setShops(List<Shop> shops) {
        this.getShops().addAll(shops);
    }

    public String toString() {
        return "Region [name = " + this.name + "]";
    }

}

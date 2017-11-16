package com.netcracker.sa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.tomcat.jni.Local;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "shops")
public class Shop {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String token;

    @NotNull
    private LocalDate lastUpdate;

    @ManyToMany(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    @JoinColumn(name = "region_id")
    @JoinTable(name = "shops_regions",
                joinColumns = @JoinColumn(name = "shop_id"),
                inverseJoinColumns = @JoinColumn(name = "region_id"))
    @JsonIgnore
    private List<Region> regions;
//
//    @OneToMany(mappedBy = "shop", fetch = FetchType.EAGER)
//    private Set<Good> goods;

    public Shop() {};

    public Shop(String name, String token, LocalDate lastUpdate) {
        this.name = name;
        this.token = token;
        this.lastUpdate = lastUpdate;
    }

    //@Transactional
    public List<Region> getRegions() {
        return regions;
    }
    //@Transactional
    public void setRegions(List<Region> regions){
        this.getRegions().addAll(regions);
    }

    public LocalDate getLastUpdate() {
        return lastUpdate;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return "Shop [name = " + this.name + "]";
    }
}

package com.netcracker.sa.entity;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "regions")
public class Region {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @NotNull
    private String name;

    @ManyToMany(mappedBy = "regions")
    private Set<Shop> shops = new HashSet<Shop>();

    public Region() {};

    public  Region(String name) {
        this.name = name;
    }

    @Transactional
    public void SetShops(Set<Shop> shops) {
        this.shops.addAll(shops);
    }

}

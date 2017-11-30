package com.netcracker.sa.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @NotNull
    private String name;

    @OneToMany(mappedBy = "categories", fetch = FetchType.EAGER)
    private List<Subcategory> subcategories;

    public Category() {}

    public  Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public List<Subcategory> getSubcategories() {
        return subcategories;
    }
}


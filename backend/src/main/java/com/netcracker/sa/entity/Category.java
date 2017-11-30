package com.netcracker.sa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> categories;

    public Category() {}

    public  Category(String name) {
        this.name = name;
    }
    public  Category(String name, Category parent) {
        this.name = name;
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }
    @JsonIgnore
    public List<Category> getCategories() {
        return categories;
    }

    public Category getParent() {
        return parent;
    }
}


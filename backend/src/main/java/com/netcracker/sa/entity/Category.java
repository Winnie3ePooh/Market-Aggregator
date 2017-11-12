package com.netcracker.sa.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @NotNull
    private String name;

    @OneToMany(mappedBy = "categories", fetch = FetchType.EAGER)
    private Collection<Subcategory> subcategories;

    public Category() {};

    public  Category(String name) {
        this.name = name;
    }

}

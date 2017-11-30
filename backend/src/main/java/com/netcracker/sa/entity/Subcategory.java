package com.netcracker.sa.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Entity
@Table(name = "subcategories")
public class Subcategory {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category categories;

    @OneToMany(mappedBy = "subcategory", fetch = FetchType.EAGER)
    private Collection<Good> goods;

    public Subcategory(){};

    public Subcategory(String name, Category cat) {
        this.name = name;
        this.categories = cat;
    }

    public Long getId(){
        return id;
    }

    public String getName(){
        return name;
    }


    public String toString() {
        return "Subcat: "+this.name+" "+this.goods;
    }
}

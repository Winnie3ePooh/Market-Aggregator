package com.netcracker.sa.entity;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "shops")
public class Shop {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @NotNull
    private String name;

    @ManyToMany(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JoinColumn(name = "region_id")
    @JoinTable(name = "shops_regions",
                joinColumns = @JoinColumn(name = "shop_id"),
                inverseJoinColumns = @JoinColumn(name = "region_id"))
    private Set<Region> regions = new HashSet<Region>();
//
//    @OneToMany(mappedBy = "shop", fetch = FetchType.EAGER)
//    private Set<Good> goods;

    public Shop() {};

    public Shop(String name) {
        this.name = name;
    }

    @Transactional
    public void SetRegions(Set<Region> regions) {
        this.regions.addAll(regions);
    }

}

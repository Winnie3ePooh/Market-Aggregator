package com.netcracker.sa.DAO;

import com.netcracker.sa.entity.Shop;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ShopRepository extends CrudRepository<Shop, Long>{

    Shop findByName(String name);

    List<Shop> findByIdIn(Long[] shopIds);

}

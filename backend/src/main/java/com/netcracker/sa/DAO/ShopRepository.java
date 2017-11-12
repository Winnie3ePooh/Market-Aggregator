package com.netcracker.sa.DAO;

import com.netcracker.sa.entity.Shop;
import org.springframework.data.repository.CrudRepository;

public interface ShopRepository extends CrudRepository<Shop, Long>{

    Shop findByName(String name);

}

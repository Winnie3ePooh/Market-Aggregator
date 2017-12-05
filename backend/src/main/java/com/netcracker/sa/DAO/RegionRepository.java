package com.netcracker.sa.DAO;

import com.netcracker.sa.entity.Region;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RegionRepository extends CrudRepository<Region, Long>{

    Region findByName(String name);

    List<Region> findAll();

}

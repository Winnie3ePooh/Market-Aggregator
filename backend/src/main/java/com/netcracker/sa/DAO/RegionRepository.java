package com.netcracker.sa.DAO;

import com.netcracker.sa.entity.Region;
import org.springframework.data.repository.CrudRepository;

public interface RegionRepository extends CrudRepository<Region, Long>{

    Region findByName(String name);

}

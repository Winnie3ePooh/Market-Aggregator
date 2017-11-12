package com.netcracker.sa.DAO;

import com.netcracker.sa.entity.Subcategory;
import org.springframework.data.repository.CrudRepository;

public interface SubcategoryRepository extends CrudRepository<Subcategory, Long>{

    Subcategory findByName(String name);

}

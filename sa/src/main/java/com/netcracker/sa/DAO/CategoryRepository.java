package com.netcracker.sa.DAO;

import com.netcracker.sa.entity.Category;
import com.netcracker.sa.entity.Good;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long>{

    Category findByName(String name);

}

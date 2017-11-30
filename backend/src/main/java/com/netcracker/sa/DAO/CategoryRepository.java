package com.netcracker.sa.DAO;

import com.netcracker.sa.entity.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Long>{

    List<Category> findByParentIsNull();
    Category findByName(String name);

}

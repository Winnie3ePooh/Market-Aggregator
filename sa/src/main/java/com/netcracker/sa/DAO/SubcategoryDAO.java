package com.netcracker.sa.DAO;

import com.netcracker.sa.entity.Subcategory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional
public class SubcategoryDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public void insert(Subcategory sub) {
        entityManager.persist(sub);
    }

}


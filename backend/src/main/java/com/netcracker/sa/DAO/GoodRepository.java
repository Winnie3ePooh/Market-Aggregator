package com.netcracker.sa.DAO;

import com.netcracker.sa.entity.Good;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface GoodRepository extends PagingAndSortingRepository<Good, Long> {

    Page<Good> findAll();

    Page<Good> findByTitleContains(String keyword, Pageable pageable);

}

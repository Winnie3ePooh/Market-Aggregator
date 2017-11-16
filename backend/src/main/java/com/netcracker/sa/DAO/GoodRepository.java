package com.netcracker.sa.DAO;

import com.netcracker.sa.entity.Good;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDate;
import java.util.List;


public interface GoodRepository extends PagingAndSortingRepository<Good, Long> {

    //Page<Good> findAll();

    List<Good> findAll();

    Good findFirstByUriOrderByIdDesc(String uri);

    Page<Good> findByTitleContainsIgnoreCase(String keyword, Pageable pageable);

    List<Good> deleteByEndDateLessThanEqual(LocalDate localDate);

}

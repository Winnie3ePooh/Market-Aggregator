package com.netcracker.sa.DAO;

import com.netcracker.sa.entity.Good;
import com.netcracker.sa.entity.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDate;
import java.util.List;


public interface GoodRepository extends PagingAndSortingRepository<Good, Long> {

    //Page<Good> findAll();

    Page<Good> findByShopIn(Pageable pageable, List<Shop> shops);

    Page<Good> findByShopInAndCategoryParentId(Pageable pageable,List<Shop> shops,Long id);

    Page<Good> findByShopInAndTitleContainsIgnoreCaseAndCategoryParentId(Pageable pageable,List<Shop> shops,String keyword,Long id);

    Good findFirstByUriOrderByIdDesc(String uri);

    Page<Good> findByShopInAndTitleContainsIgnoreCase(List<Shop> shops, String keyword, Pageable pageable);

    List<Good> deleteByEndDateLessThanEqual(LocalDate localDate);

}

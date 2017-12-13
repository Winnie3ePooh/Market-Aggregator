package com.netcracker.sa.controller;

import com.google.gson.Gson;
import com.netcracker.sa.DAO.GoodRepository;
import com.netcracker.sa.DAO.RegionRepository;
import com.netcracker.sa.DAO.ShopRepository;
import com.netcracker.sa.entity.Good;
import com.netcracker.sa.entity.Region;
import com.netcracker.sa.entity.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class GoodsController {

    private List<Shop> shops;

    private List<Sort> sortOptions = new ArrayList<Sort>();
    private Boolean checkSort = false;

    @Autowired
    private GoodRepository goodRep;
    @Autowired
    private RegionRepository regionRep;
    @Autowired
    private ShopRepository shopRep;

    @RequestMapping(path = "/getShops")
    public @ResponseBody List<Shop> getShopsByRegion(@RequestParam(value = "region", defaultValue = "WW") String region) {
        Region reg = regionRep.findByName(region);
        shops = reg.getShops();
        if(!checkSort){
            checkSort = true;
            sortOptions.add(new Sort(Sort.Direction.ASC,"title"));
            sortOptions.add(new Sort(Sort.Direction.DESC,"title"));
            sortOptions.add(new Sort(Sort.Direction.ASC,"price"));
            sortOptions.add(new Sort(Sort.Direction.DESC,"price"));
            sortOptions.add(new Sort(Sort.Direction.ASC,"creationDate"));
            sortOptions.add(new Sort(Sort.Direction.DESC,"creationDate"));
        }
        return shops;
    };

    @RequestMapping(path = "/getAll")
    public @ResponseBody Page<Good> getGoodForMainPage(@RequestParam(value = "page", defaultValue = "0") String page,
                                                       @RequestParam(value = "category", defaultValue = "All") String category,
                                                       @RequestParam(value = "sort", defaultValue = "-1") int sortID) {
        System.out.println(shops);
        Sort sortParam;
        if(sortID != -1) {
            sortParam = sortOptions.get(sortID);
        } else {
            sortParam = new Sort(Sort.Direction.ASC, "id");
        }
        Pageable pageable = new PageRequest(Integer.parseInt(page),18, sortParam);
        if(category.equals("All")) {
            Page<Good> resultPage = goodRep.findByShopIn(pageable,shops);
            System.out.println(shops);
            return resultPage;
        } else {
            Page<Good> resultPage = goodRep.findByShopInAndCategoryParentId(pageable,shops, Long.parseLong(category));
            System.out.println(shops);
            return resultPage;
        }
    };

    @RequestMapping(path = "/findGoods")
    public @ResponseBody Page<Good> getGoodsByKeyword(@RequestParam(value = "keyword", defaultValue = "All") String keyword,
                                                      @RequestParam(value = "page", defaultValue = "0") String page) {
        Pageable pageable = new PageRequest(Integer.parseInt(page),18);
        Page<Good> results = goodRep.findByShopInAndTitleContainsIgnoreCase(shops,keyword,pageable);
        return results;
    };

    @RequestMapping(path = "/findGoodsBySubcategory")
    public @ResponseBody Page<Good> findGoodsBySubcategory(@RequestParam(value = "category") String subcategory,
                                                           @RequestParam(value = "keyword", defaultValue = "") String keyword,
                                                           @RequestParam(value = "page", defaultValue = "0") String page,
                                                           @RequestParam(value = "sort", defaultValue = "-1") int sortID) {
        Pageable pageable = new PageRequest(Integer.parseInt(page),18);
        try {
            Page<Good> results  = goodRep.findByShopInAndTitleContainsIgnoreCaseAndCategoryParentId(pageable, shops, keyword, Long.parseLong(subcategory));
            return results;
        } catch (Exception e) {
            Page<Good> results  = goodRep.findByShopInAndTitleContainsIgnoreCase(pageable, shops, keyword);
            return results;
        }


    };

    @RequestMapping(path = "/setShops", method = RequestMethod.POST)
    public @ResponseBody String setSelectedShops(@RequestBody Long[] shops_id){
        shops = shopRep.findByIdIn(shops_id);
        for(Shop i: shops){
            System.out.println(i);
        };
        return "ok";
    };

}

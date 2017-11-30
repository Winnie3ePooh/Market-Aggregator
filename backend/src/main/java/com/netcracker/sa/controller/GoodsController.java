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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class GoodsController {

    private List<Shop> shops;

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
        return shops;
    };

    @RequestMapping(path = "/getAll")
    public @ResponseBody Page<Good> getGoodForMainPage(@RequestParam(value = "page", defaultValue = "0") String page,
                                                       @RequestParam(value = "category", defaultValue = "All") String category) {


        if(category.equals("All")) {
            Page<Good> resultPage = goodRep.findByShopIn(new PageRequest(Integer.parseInt(page),18),shops);
            System.out.println(shops);
            return resultPage;
        } else {
            Page<Good> resultPage = goodRep.findBySubcategoryCategoriesAndShopIn(new PageRequest(Integer.parseInt(page),18),shops, Long.parseLong(category));
            System.out.println(shops);
            return resultPage;
        }
    };

    @RequestMapping(path = "/findGoods")
    public @ResponseBody Page<Good> getGoodsByKeyword(@RequestParam(value = "keyword") String keyword,
                                                      @RequestParam(value = "page", defaultValue = "0") String page) {
        Pageable pageable = new PageRequest(Integer.parseInt(page),18);
        Page<Good> results = goodRep.findByShopInAndTitleContainsIgnoreCase(shops,keyword,pageable);
        return results;
    };

    @RequestMapping(path = "/findGoodsBySubcategory")
    public @ResponseBody Page<Good> findGoodsBySubcategory(@RequestParam(value = "keyword") String keyword,
                                                           @RequestParam(value = "subcategory") String subcategory,
                                                           @RequestParam(value = "page", defaultValue = "0") String page) {
        Pageable pageable = new PageRequest(Integer.parseInt(page),18);
        Page<Good> results  = goodRep.findByShopInAndTitleContainsIgnoreCaseAndSubcategoryId(pageable, shops, keyword, Long.parseLong(subcategory));
        return results;
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

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

        @Autowired
        private GoodRepository goodRep;
        @Autowired
        private RegionRepository regionRep;

        @RequestMapping(path = "/getShops")
        public @ResponseBody List<Shop> getShopsByRegion(@RequestParam(value = "region", defaultValue = "WW") String region) {
                Region reg = regionRep.findByName(region);
                List<Shop> shops = reg.getShops();
                return shops;
        };

        @RequestMapping(path = "/getAll")
        public @ResponseBody Page<Good> getGoodForMainPage(@RequestParam(value = "page", defaultValue = "0") String page) {
                Page<Good> resultPage = goodRep.findAll(new PageRequest(Integer.parseInt(page),18));
                return resultPage;
        };

        @RequestMapping(path = "/findGoods")
        public @ResponseBody Page<Good> getGoodsByKeyword(@RequestParam(value = "keyword") String keyword,
                                                          @RequestParam(value = "page", defaultValue = "0") String page) {
                Pageable pageable = new PageRequest(Integer.parseInt(page),18);
                Page<Good> resultPage = goodRep.findByTitleContainsIgnoreCase(keyword,pageable);
                return resultPage;
        };

}

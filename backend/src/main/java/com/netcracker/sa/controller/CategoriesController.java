package com.netcracker.sa.controller;


import com.netcracker.sa.DAO.CategoryRepository;
import com.netcracker.sa.DAO.GoodRepository;
import com.netcracker.sa.DAO.RegionRepository;
import com.netcracker.sa.entity.Category;
import com.netcracker.sa.entity.Region;
import com.netcracker.sa.entity.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class CategoriesController {

    @Autowired
    private CategoryRepository categoryRep;

    @RequestMapping(path = "/getAllCategories")
    public @ResponseBody
    List<Category> getAllCategories(@RequestParam(value = "region", defaultValue = "WW") String region) {
        return (List<Category>) categoryRep.findByParentIsNull();
    };

}

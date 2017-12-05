package com.netcracker.sa.controller;


import com.netcracker.sa.DAO.CategoryRepository;
import com.netcracker.sa.DAO.RegionRepository;
import com.netcracker.sa.entity.Category;
import com.netcracker.sa.entity.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class RegionController {

    @Autowired
    private RegionRepository regionRep;

    @RequestMapping(path = "/getAllRegions")
    public @ResponseBody List<Region> getAllRegions() {
        return regionRep.findAll();
    };

}

package com.netcracker.sa.controller;

import com.google.gson.Gson;
import com.netcracker.sa.DAO.GoodRepository;
import com.netcracker.sa.DAO.GoodRequestRepository;
import com.netcracker.sa.DAO.RegionRepository;
import com.netcracker.sa.DAO.ShopRepository;
import com.netcracker.sa.entity.Good;
import com.netcracker.sa.entity.GoodRequest;
import com.netcracker.sa.entity.Region;
import com.netcracker.sa.entity.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping(path = "/api/requests")
public class GoodRequestController {

    @Autowired
    private GoodRequestRepository goodReqRep;

    @RequestMapping(path = "/addNewRequest", method = RequestMethod.POST)
    public @ResponseBody String setSelectedShops(@RequestBody GoodRequest goodRequest){
        try {
            LocalDateTime now = LocalDateTime.now();//For reference
            goodRequest.setToken(now+goodRequest.getName());
            goodRequest.setTime(LocalDate.now());
            goodReqRep.save(goodRequest);
            return "200";
        } catch (Exception e) {
            return "404";
        }
    };

}
package com.netcracker.sa.scheduler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

public class asd {

    private static String USER_AGENT = "Mozilla/5.0";
    private static String url = "http://svcs.ebay.com/services/search/FindingService/v1?OPERATION-NAME=findItemsByCategory&SERVICE-VERSION=1.0.0&SECURITY-APPNAME=MaximSte-Salesagg-PRD-5132041a0-a837000d&RESPONSE-DATA-FORMAT=XML&REST-PAYLOAD=&categoryId=10181&paginationInput.entriesPerPage=100&paginationInput.pageNumber=1";


    public static void main(String[] args) {

        String dateInString = "2017-10-05T15:23:01Z";

        Instant instant = Instant.parse(dateInString);
        LocalDate result = LocalDateTime.ofInstant(instant, ZoneId.of(ZoneOffset.UTC.getId())).toLocalDate();

//        System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(Date.from(instant)));

//        LocalDateTime result = LocalDateTime.ofInstant(instant, ZoneId.of(ZoneOffset.UTC.getId()));

        System.out.println(result);

//        System.out.println(result);
//
//        //get date time + timezone
//        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.of("Africa/Tripoli"));
//        System.out.println(zonedDateTime);
//
//        //get date time + timezone
//        ZonedDateTime zonedDateTime2 = instant.atZone(ZoneId.of(ZoneOffset.UTC.getId()));
//        System.out.println(zonedDateTime2);
//
//        ZonedDateTime utc = ZonedDateTime.now(ZoneOffset.UTC);
//        Date date = Date.from(utc.toInstant());
//        System.out.println(utc.compareTo(zonedDateTime2));


    }

}


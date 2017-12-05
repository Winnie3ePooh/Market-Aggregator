package com.netcracker.sa.scheduler;

import com.netcracker.sa.entity.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class asd {

    private static String USER_AGENT = "Mozilla/5.0";
    private static String url = "http://svcs.ebay.com/services/search/FindingService/v1?OPERATION-NAME=findItemsByCategory&SERVICE-VERSION=1.0.0&SECURITY-APPNAME=MaximSte-Salesagg-PRD-5132041a0-a837000d&RESPONSE-DATA-FORMAT=XML&REST-PAYLOAD=&categoryId=10181&paginationInput.entriesPerPage=100&paginationInput.pageNumber=";


    public static void main(String[] args) {

        xmlParse();
    }

    public static String sendRequest(int pageNumber, String... params) {
        try {
            URL obj = new URL(url+pageNumber);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            // optional default is GET
            con.setRequestMethod("GET");
            //add request header
            con.setRequestProperty("User-Agent", USER_AGENT);
            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url + pageNumber);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println(response.toString());
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Scheduled(fixedRate = 3000000)
    public static void xmlParse() {
        try {
            String xmlRecords = sendRequest(1);

            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xmlRecords));

            Document doc = db.parse(is);
            NodeList page = doc.getElementsByTagName("paginationOutput");
            System.out.println("------------------------------------------------------------------------------");
            parseResponse(doc);

            System.out.println(getCharacterDataFromElement((Element) page.item(0), "categoryName"));
            for(int j = 2; j <= Integer.parseInt(getCharacterDataFromElement((Element) page.item(0), "totalPages")); j++) {
                System.out.println("222222222222222222222222222222222");
                xmlRecords = sendRequest(j);

                is.setCharacterStream(new StringReader(xmlRecords));

                doc = db.parse(is);
                parseResponse(doc);
            };

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

    }

    public static void parseResponse(Document doc) {
        NodeList nodes = doc.getElementsByTagName("item");

        for (int i = 0; i < nodes.getLength(); i++) {
            Element element = (Element) nodes.item(i);

            System.out.println(getCharacterDataFromElement(element, "itemId"));
        }
    }

    public static String getCharacterDataFromElement(Element element,String nodeName) {
        try {
            NodeList name = element.getElementsByTagName(nodeName);
            Element e = (Element) name.item(0);
            org.w3c.dom.Node child = e.getFirstChild();
            if (child instanceof org.w3c.dom.CharacterData) {
                org.w3c.dom.CharacterData cd = (org.w3c.dom.CharacterData) child;
                if(nodeName.equals("endTime") || nodeName.equals("startTime")){
                    Instant instant = Instant.parse(cd.getData());
                    return String.valueOf(LocalDateTime.ofInstant(instant, ZoneId.of(ZoneOffset.UTC.getId())).toLocalDate());
                } else {
                    return cd.getData();
                }

            }
            return "";
        } catch (NullPointerException e) {
            return "null";
        }

    }

}


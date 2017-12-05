package com.netcracker.sa.scheduler;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;


import com.netcracker.sa.DAO.*;
import com.netcracker.sa.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


@Component
public class EbayScheduler {

    @Autowired
    private GoodRepository goodRep;
    @Autowired
    private ShopRepository shopRep;
    @Autowired
    private CategoryRepository categoryRep;
    @Autowired
    private RegionRepository regionRep;
    @Autowired
    private ImageRepository imgRep;

    private final String USER_AGENT = "Mozilla/5.0";
    private final String catList = "http://open.api.ebay.com/Shopping?callname=GetCategoryInfo&appid=MaximSte-Salesagg-PRD-5132041a0-a837000d&siteid=3&CategoryID=-1&version=729&IncludeSelector=ChildCategories";
    private final String url = "http://svcs.ebay.com/services/search/FindingService/v1?OPERATION-NAME=findItemsByCategory&SERVICE-VERSION=1.0.0&SECURITY-APPNAME=MaximSte-Salesagg-PRD-5132041a0-a837000d&RESPONSE-DATA-FORMAT=XML&REST-PAYLOAD=&categoryId=293&paginationInput.entriesPerPage=100&paginationInput.pageNumber=";
    private final String catName = "&categoryId=";

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    public void getNewItems() throws IOException {

    }

    public String sendRequest(String urls) {
        try {
            URL obj = new URL(urls);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            // optional default is GET
            con.setRequestMethod("GET");
            //add request header
            con.setRequestProperty("User-Agent", USER_AGENT);
            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + urls);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //@Scheduled(fixedRate = 30000000)
    public void xmlParse() {
        try {
            log.info("BEGINNING", dateFormat.format(new Date()));

            Region reg = regionRep.findByName("WW");
            if(reg == null){
                regionRep.save(new Region("WW"));
                reg = regionRep.findByName("WW");
            }

            List<Region> regs = new ArrayList<Region>();
            regs.add(reg);

            shopRep.save(new Shop("Ebay","asdasd",LocalDate.now()));
            Shop shop = shopRep.findByName("Ebay");
            if(shop == null){
                shopRep.save(new Shop("Ebay","asdasd",LocalDate.now()));
                shop = shopRep.findByName("Ebay");
            }

            List<Shop> shops = new ArrayList<Shop>();
            shops.add(shop);

            shop.setRegions(regs);
            reg.setShops(shops);
            regionRep.save(reg);
            shopRep.save(shop);

            //Гуляем по категориям
            String xmlRecords = sendRequest(catList);

            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xmlRecords));

            Document doc = db.parse(is);
            NodeList nodes = doc.getElementsByTagName("Category");

            for (int i = 0; i < nodes.getLength(); i++) {
                Element element = (Element) nodes.item(i);
                String categoryName = getCharacterDataFromElement(element, "CategoryName");
                Category cat = categoryRep.findByName(categoryName);
                if(cat == null){
                    categoryRep.save(new Category(categoryName));
                    cat = categoryRep.findByName(categoryName);
                }

                xmlRecords = sendRequest(url+"1"+catName+getCharacterDataFromElement(element, "CategoryID"));

                db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                is = new InputSource();
                is.setCharacterStream(new StringReader(xmlRecords));

                doc = db.parse(is);
                NodeList page = doc.getElementsByTagName("paginationOutput");
                System.out.println("------------------------------------------------------------------------------");
                parseResponse(doc,cat,shop);

                System.out.println(getCharacterDataFromElement((Element) page.item(0), "categoryName"));
                //Integer.parseInt(getCharacterDataFromElement((Element) page.item(0), "totalPages"))
                for(int j = 2; j <= 10; j++) {
                    System.out.println("222222222222222222222222222222222");
                    xmlRecords = sendRequest(url+j+catName+getCharacterDataFromElement(element, "CategoryID"));

                    is.setCharacterStream(new StringReader(xmlRecords));

                    doc = db.parse(is);
                    parseResponse(doc,cat,shop);
                };

            }

            log.info("ENDING", dateFormat.format(new Date()));

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

    }

    public void parseResponse(Document doc, Category cat, Shop shop) {
        NodeList nodes = doc.getElementsByTagName("item");

        for (int i = 0; i < nodes.getLength(); i++) {
            Element element = (Element) nodes.item(i);
            Category category = categoryRep.findByName(getCharacterDataFromElement(element, "categoryName"));
            if(category == null){
                categoryRep.save(new Category(getCharacterDataFromElement(element, "categoryName"),cat));
                category = categoryRep.findByName(getCharacterDataFromElement(element, "categoryName"));
            }
            System.out.println(category);
            goodRep.save(new Good(getCharacterDataFromElement(element, "itemId"),
                    getCharacterDataFromElement(element, "title"),getCharacterDataFromElement(element, "title"),
                    getCharacterDataFromElement(element, "startTime"), getCharacterDataFromElement(element, "endTime"),
                    Float.parseFloat(getCharacterDataFromElement(element, "currentPrice")), "USD",
                    getCharacterDataFromElement(element, "viewItemURL"), category, shop)
            );

            Good good = goodRep.findFirstByUriOrderByIdDesc(getCharacterDataFromElement(element, "itemId"));
            Image img = imgRep.save(new Image(getCharacterDataFromElement(element, "galleryURL")));
            List<Image> imgs = new ArrayList<Image>();
            imgs.add(img);
            img.setGood(good);
            good.setImages(imgs);
            goodRep.save(good);
            imgRep.save(img);
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

    //@Scheduled(fixedRate = 100000)
    public void updateDB() {
        //Shop shop = shopRep.findByName("Ebay");
        //String params = "&StartTimeFrom"
        //goodRep.deleteByEndDateLessThanEqual(LocalDate.now());
        //System.out.println(shop.getLastUpdate().atStartOfDay(ZoneOffset.UTC));
//        Iterable<Subcategory> sub = subcategoryRep.findAll();
//        for(Subcategory s: sub){
//            System.out.println(s);
//        }
    }

}

package com.netcracker.sa.scheduler;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.netcracker.sa.DAO.*;
import com.netcracker.sa.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


@Component
public class EbayScheduler {

    @Autowired
    private GoodRepository goodRep;
    @Autowired
    private SubcategoryRepository subcategoryRep;
    @Autowired
    private ShopRepository shopRep;
    @Autowired
    private CategoryRepository categoryRep;
    @Autowired
    private RegionRepository regionRep;

    private final String USER_AGENT = "Mozilla/5.0";
    private final String url = "http://svcs.ebay.com/services/search/FindingService/v1?OPERATION-NAME=findItemsByCategory&SERVICE-VERSION=1.0.0&SECURITY-APPNAME=MaximSte-Salesagg-PRD-5132041a0-a837000d&RESPONSE-DATA-FORMAT=XML&REST-PAYLOAD=&categoryId=293&paginationInput.entriesPerPage=100&paginationInput.pageNumber=";

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    public void getNewItems() throws IOException {

    }

    public String sendRequest(int pageNumber) {
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

            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Scheduled(fixedRate = 1800000)
    private void xmlParse() {
        try {
            log.info("BEGINNING", dateFormat.format(new Date()));
            String xmlRecords = sendRequest(1);

            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xmlRecords));

//            regionRep.save(new Region("WW"));
//            Region reg = regionRep.findByName("WW");
//
//            Set<Region> regs = new HashSet<Region>();
//            regs.add(reg);
//
//            shopRep.save(new Shop("Ebay"));
//            Shop shop = shopRep.findByName("Ebay");
//
//            Set<Shop> shops = new HashSet<Shop>();
//            shops.add(shop);
//
//            shop.SetRegions(regs);
//            reg.SetShops(shops);


            categoryRep.save(new Category("Electronics"));
            Category cat = categoryRep.findByName("Electronics");
            System.out.println(cat);

            Document doc = db.parse(is);
            NodeList page = doc.getElementsByTagName("paginationOutput");
            parseResponse(doc,cat);



            for(int j = 2; j <= Integer.parseInt(getCharacterDataFromElement((Element) page.item(0), "totalPages")); j++) {
                xmlRecords = sendRequest(j);

                is.setCharacterStream(new StringReader(xmlRecords));

                doc = db.parse(is);
                parseResponse(doc,cat);
            };
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

    public void parseResponse(Document doc, Category cat) {
        NodeList nodes = doc.getElementsByTagName("item");

        for (int i = 0; i < nodes.getLength(); i++) {
            Element element = (Element) nodes.item(i);
            Subcategory sub = subcategoryRep.findByName(getCharacterDataFromElement(element, "categoryName"));
            if(sub == null){
                subcategoryRep.save(new Subcategory(getCharacterDataFromElement(element, "categoryName"),cat));
                sub = subcategoryRep.findByName(getCharacterDataFromElement(element, "categoryName"));
            }
            System.out.println(sub);
            goodRep.save(new Good(getCharacterDataFromElement(element, "title"),getCharacterDataFromElement(element, "title"),
                    getCharacterDataFromElement(element, "startTime"),Float.parseFloat(getCharacterDataFromElement(element, "currentPrice")),
                    getCharacterDataFromElement(element, "galleryURL"),getCharacterDataFromElement(element, "viewItemURL"),
                    sub));
        }
    }

    public static String getCharacterDataFromElement(Element element,String nodeName) {
        try {
            NodeList name = element.getElementsByTagName(nodeName);
            Element e = (Element) name.item(0);
            org.w3c.dom.Node child = e.getFirstChild();
            if (child instanceof org.w3c.dom.CharacterData) {
                org.w3c.dom.CharacterData cd = (org.w3c.dom.CharacterData) child;
                return cd.getData();
            }
            return "";
        } catch (NullPointerException e) {
            return "null";
        }

    }

}

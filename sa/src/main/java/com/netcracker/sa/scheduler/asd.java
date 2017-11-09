package com.netcracker.sa.scheduler;

import org.dom4j.DocumentException;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

public class asd {

    private static String USER_AGENT = "Mozilla/5.0";
    private static String url = "http://svcs.ebay.com/services/search/FindingService/v1?OPERATION-NAME=findItemsByCategory&SERVICE-VERSION=1.0.0&SECURITY-APPNAME=MaximSte-Salesagg-PRD-5132041a0-a837000d&RESPONSE-DATA-FORMAT=XML&REST-PAYLOAD=&categoryId=10181&paginationInput.entriesPerPage=100&paginationInput.pageNumber=1";


    public static void main(String[] args) throws DocumentException, IOException, ParserConfigurationException, SAXException {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        // optional default is GET
        con.setRequestMethod("GET");
        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);
        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        //print result
        System.out.println(response.toString());

        String xmlRecords = response.toString();

        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        InputSource is = new InputSource();
        is.setCharacterStream(new StringReader(xmlRecords));

        Document doc = db.parse(is);
        NodeList nodes = doc.getElementsByTagName("item");

        for (int i = 0; i < nodes.getLength(); i++) {
            Element element = (Element) nodes.item(i);
            System.out.println("Name: " + getCharacterDataFromElement(element, "title"));
        }

    }

    public static String getCharacterDataFromElement(Element element,String nodeName) {
        NodeList name = element.getElementsByTagName(nodeName);
        Element e = (Element) name.item(0);
        org.w3c.dom.Node child = e.getFirstChild();
        if (child instanceof org.w3c.dom.CharacterData) {
            org.w3c.dom.CharacterData cd = (org.w3c.dom.CharacterData) child;
            return cd.getData();
        }
        return "";
    }
}


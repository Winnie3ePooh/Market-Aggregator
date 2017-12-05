package com.netcracker.sa.scheduler;

import com.netcracker.sa.DAO.GoodRepository;
import com.netcracker.sa.DAO.GoodRequestRepository;
import com.netcracker.sa.entity.Good;
import com.netcracker.sa.entity.GoodRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    @Autowired
    private GoodRequestRepository goodReqRep;
    @Autowired
    private GoodRepository goodRep;

    @Scheduled(fixedRate = 10000)
    public void findRequestsAndSend(){
        List<GoodRequest> requests = (List<GoodRequest>) goodReqRep.findAll();
        if(!requests.isEmpty()){
            for(GoodRequest request: requests) {
                LocalDate now = request.getTime();
                List<Good> newGoods = goodRep.findByCreationDateGreaterThan(now);
                if(newGoods.isEmpty()){
                    System.out.println("nope");
                }
                else {
                    System.out.println("----------------------------------Нашёл");
                    for(Good good: newGoods){
                        System.out.println(good);
                    }
                    sendEmail(request);
                }
            }
        }
    }

    public void sendEmail(GoodRequest request) {
        // Recipient's email ID needs to be mentioned.
        String to = request.getEmail();
        // Sender's email ID needs to be mentioned
        String from = "maks1893@gmail.com";
        // Assuming you are sending email from localhost
        String host = "localhost";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", host);

        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("This is the Subject Line!");

            // Now set the actual message
            message.setText("This is actual message");

            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

}

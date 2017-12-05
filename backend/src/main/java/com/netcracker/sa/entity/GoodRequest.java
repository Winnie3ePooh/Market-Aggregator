package com.netcracker.sa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.tomcat.jni.Local;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "goodrequests")
public class GoodRequest {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String email;

    @NotNull
    private String keyword;

    @NotNull
    private String token;

    @NotNull
    private LocalDate time;

    public GoodRequest() {};

    public GoodRequest(String name, String email, String keyword, String token, LocalDate time) {
        this.name = name;
        this.email = email;
        this.keyword = keyword;
        this.token = token;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getToken() {
        return token;
    }

    public LocalDate getTime() {
        return time;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setTime(LocalDate time) {
        this.time = time;
    }

    public String toString() {
        return "Request [Who = " + this.name + "; Email = " + this.email + "; Keyword = " + this.keyword + "]";
    }

}

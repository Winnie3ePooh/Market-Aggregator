package com.netcracker.sa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Ng2bootApplication {

	public static void main(String[] args) {
		SpringApplication.run(Ng2bootApplication.class, args);
	}
}

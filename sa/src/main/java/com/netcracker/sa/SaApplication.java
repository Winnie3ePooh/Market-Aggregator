package com.netcracker.sa;

import com.netcracker.sa.DAO.SubcategoryRepository;
import com.netcracker.sa.entity.Subcategory;
import com.netcracker.sa.scheduler.EbayScheduler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;

@SpringBootApplication
@EnableScheduling
public class SaApplication {

	private static SubcategoryRepository repS;

	public static void main(String[] args) throws IOException {
		SpringApplication.run(SaApplication.class, args);
	}
}

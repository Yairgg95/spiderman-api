package com.spiderverse.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class SpiderverseApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpiderverseApiApplication.class, args);
	}

}

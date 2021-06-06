package com.lucas.matches;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class MatchesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MatchesApplication.class, args);
	}

}

package com.lucas.matches;

import com.lucas.matches.infrastructure.util.DatabaseSeed;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;

@EnableCaching
@SpringBootApplication
public class MatchesApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(MatchesApplication.class, args);
		context.getBean(DatabaseSeed.class).seedDatabase();
	}

}

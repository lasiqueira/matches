package com.lucas.matches

import com.lucas.matches.infrastructure.util.DatabaseSeed
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cache.annotation.EnableCaching

@EnableCaching
@SpringBootApplication
class MatchesApplication

    fun main(args: Array<String>) {
        val context = SpringApplication.run(MatchesApplication::class.java, *args)
        context.getBean(DatabaseSeed::class.java).seedDatabase()
    }

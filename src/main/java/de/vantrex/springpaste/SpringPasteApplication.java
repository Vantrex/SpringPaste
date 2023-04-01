package de.vantrex.springpaste;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
public class SpringPasteApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringPasteApplication.class, args);
    }

}

package de.vantrex.springpaste.config;

import de.vantrex.springpaste.model.paste.Paste;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

import java.util.Optional;

@Configuration
@EnableMongoAuditing
public class MongoAuditing {

    @Bean
    public AuditorAware<Paste> pasteProvider() {
        return Optional::empty;
    }
}
package com.chamberos.chamberosapi.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import com.chamberos.chamberosapi.domain.User;
import com.chamberos.chamberosapi.domain.Location;
import com.chamberos.chamberosapi.infra.outputport.UserRepository;

@EnableMongoRepositories(basePackageClasses = UserRepository.class)
@Configuration
public class MongoDBConfig {

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository) {
        return strings -> {
            Location location = new Location(123, 123);
            User user = new User(null, "Peter", "123", location);
            userRepository.save(user);
        };
    }
}
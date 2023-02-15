package com.chamberos.chamberosapi.infra.outputport;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.chamberos.chamberosapi.domain.User;

public interface UserRepository extends MongoRepository<User, String> {
    public User getById(String id);
}

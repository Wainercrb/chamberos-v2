package com.chamberos.chamberosapi.infra.outputport;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.chamberos.chamberosapi.domain.User;

public interface UserRepository extends MongoRepository<User, String> {
    public User getById(String userId);
    public List<User> getAll();
}

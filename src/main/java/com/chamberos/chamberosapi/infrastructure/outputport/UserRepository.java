package com.chamberos.chamberosapi.infrastructure.outputport;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import com.chamberos.chamberosapi.domain.User;

public interface UserRepository extends MongoRepository<User, String> {
    public User getById(String id);
    
    @Query("{ 'email' : ?0, 'password' : ?1 }")
    public User findByEmailAndPassword(String email, String password);
}

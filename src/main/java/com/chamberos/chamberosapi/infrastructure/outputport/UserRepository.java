package com.chamberos.chamberosapi.infrastructure.outputport;

import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import com.chamberos.chamberosapi.domain.model.User;
import org.springframework.data.geo.Point;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Distance;

public interface UserRepository extends MongoRepository<User, String> {
    @Query(fields = "{ 'password' : 0 }")
    public User getById(String id);

    public User findByEmail(String email);

    public User findByUsername(String username);

    @Query(fields = "{ 'password' : 0 }")
    public List<User> findByFullName(String fullName, Pageable pageable);

    @Query(fields = "{ 'password' : 0 }")
    List<User> findByLocationNearAndProfessionsIn(
            Point location,
            Distance distance,
            List<ObjectId> professions);
}

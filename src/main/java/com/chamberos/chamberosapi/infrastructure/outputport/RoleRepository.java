package com.chamberos.chamberosapi.infrastructure.outputport;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.chamberos.chamberosapi.domain.model.UserRole;
import org.springframework.data.domain.Pageable;

public interface RoleRepository extends MongoRepository<UserRole, String> {

    public UserRole findByName(String name);

    public UserRole getById(String id);

    public List<UserRole> findByNameRegex(String nameRegex, Pageable pageable);
}

package com.chamberos.chamberosapi.infrastructure.outputport;

import java.util.List;
import com.chamberos.chamberosapi.domain.Profession;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProfessionRepository extends MongoRepository<Profession, String> {
    public Profession getById(String id);

    public List<Profession> findByNameRegex(String nameRegex, Pageable pageable);
}

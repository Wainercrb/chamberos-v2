package com.chamberos.chamberosapi.infrastructure.inputport;

import java.util.List;
import org.springframework.data.domain.Pageable;
import com.chamberos.chamberosapi.domain.model.Profession;

public interface ProfessionInputPort {
    public Profession save(Profession profession);

    public Profession getById(String userId);

    public List<Profession> getAll(String name, Pageable pageable);
}

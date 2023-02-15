package com.chamberos.chamberosapi.infrastructure.inputport;

import java.util.List;

import com.chamberos.chamberosapi.domain.Profession;
import org.springframework.data.domain.Pageable;

public interface ProfessionInputPort {
    public Profession save(Profession profession);
    public Profession getById(String userId);
    public List<Profession> getAll(String name, Pageable pageable);
}

package com.chamberos.chamberosapi.application;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import com.chamberos.chamberosapi.domain.Profession;
import com.chamberos.chamberosapi.infrastructure.inputport.ProfessionInputPort;
import com.chamberos.chamberosapi.infrastructure.outputport.ProfessionRepository;
import org.springframework.stereotype.Service;

@Service
public class ProfessionUseCase implements ProfessionInputPort {

    @Autowired
    ProfessionRepository professionRepository;

    @Override
    public Profession save(Profession profession) {
        return professionRepository.save(profession);
    }

    @Override
    public Profession getById(String professionId) {
        return professionRepository.getById(professionId);
    }

    @Override
    public List<Profession> getAll(String name, Pageable pageable) {
        return professionRepository.findByNameRegex(name, pageable);
    }
}

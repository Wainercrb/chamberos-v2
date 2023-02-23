package com.chamberos.chamberosapi.application.usecase;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import com.chamberos.chamberosapi.domain.model.UserRole;
import com.chamberos.chamberosapi.infrastructure.inputport.RoleInputPort;
import com.chamberos.chamberosapi.infrastructure.outputport.RoleRepository;

@Component
public class RoleUseCase implements RoleInputPort {

    @Autowired(required = true)
    RoleRepository roleRepository;

    @Override
    public UserRole save(UserRole role) {
        return roleRepository.save(role);
    }

    @Override
    public UserRole getById(String roleId) {
        return roleRepository.getById(roleId);
    }

    @Override
    public List<UserRole> getAll(String name, Pageable pageable) {
        return roleRepository.findByNameRegex(name, pageable);
    }
}

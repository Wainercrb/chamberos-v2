package com.chamberos.chamberosapi.infrastructure.inputport;

import java.util.List;
import org.springframework.data.domain.Pageable;
import com.chamberos.chamberosapi.domain.model.UserRole;

public interface RoleInputPort {
    public UserRole save(UserRole role);

    public UserRole getById(String userId);

    public List<UserRole> getAll(String name, Pageable pageable);
}

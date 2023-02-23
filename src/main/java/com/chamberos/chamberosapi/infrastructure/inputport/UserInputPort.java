package com.chamberos.chamberosapi.infrastructure.inputport;

import java.util.List;
import org.springframework.data.domain.Pageable;
import com.chamberos.chamberosapi.domain.model.User;

public interface UserInputPort {

    public User findByUsername(String username);

    public User findByEmail(String email);

    public User register(User user);

    public User getById(String userId);

    public List<User> getAll(String fullName, Pageable pageable);

    public List<User> findByLocationNear(
            double latitude,
            double longitude,
            double radiusInKilometers,
            String professionIds);
}

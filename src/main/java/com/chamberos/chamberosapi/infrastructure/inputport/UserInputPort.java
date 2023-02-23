package com.chamberos.chamberosapi.infrastructure.inputport;

import java.util.List;
import com.chamberos.chamberosapi.domain.User;
import org.springframework.data.domain.Pageable;

public interface UserInputPort {
    public User register(User user);
    public User authenticate(User user);
    public User getById(String userId);
    public List<User> getAll(String fullName, Pageable pageable);
    public List<User> findByLocationNear(double latitude, double longitude, double radiusInKilometers, String professionIds);
}

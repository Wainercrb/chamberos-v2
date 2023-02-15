package com.chamberos.chamberosapi.infrastructure.inputport;

import java.util.List;
import com.chamberos.chamberosapi.domain.User;

public interface UserInputPort {
    public User register(User user);
    public User authenticate(User user);
    public User getById(String userId);
    public List<User> getAll();
}

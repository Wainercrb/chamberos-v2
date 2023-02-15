package com.chamberos.chamberosapi.infra.inputport;

import java.util.List;
import com.chamberos.chamberosapi.domain.User;

public interface UserInputPort {
    public User createUser(User user);
    public User getById(String userId);
    public List<User> getAll();
}

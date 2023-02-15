package com.chamberos.chamberosapi.application;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.chamberos.chamberosapi.domain.User;
import com.chamberos.chamberosapi.infra.inputport.UserInputPort;
import com.chamberos.chamberosapi.infra.outputport.UserRepository;

@Component
public class UserUseCase implements UserInputPort {

    @Autowired
    UserRepository userRepository;
    
    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getById(String userId) {
        return userRepository.getById(userId);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }    
}
